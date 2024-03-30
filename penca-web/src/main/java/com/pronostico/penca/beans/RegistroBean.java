package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.Usuario;

@ManagedBean(name = "registroBean")
@ViewScoped
public class RegistroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	private Usuario usuarioNuevo;
	private String repetirPassword;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;
	
	final static Logger logger = Logger.getLogger(RegistroBean.class);

	@PostConstruct
	public void init() {
		try {
			logger.info("RegistroBean init()");
			usuarioNuevo = new Usuario();
		} catch (Exception e) {
			logger.error("Error al cargar el registrar", e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar la página de registro. Vuelva a intetarlo más tarde.');");
		}

	}

	public void registrar() {
		try {
			if ("".equals(usuarioNuevo.getNombre()) || "".equals(usuarioNuevo.getUsuario()) || "".equals(usuarioNuevo.getPassword())) {
				logger.error("Debe ingresar nombre, usuario y password");
				PrimeFaces.current().executeScript("error('Los campos de nombre, usuario y contraseña son obligatorios para el registro.');");
				return;
			}
			
			if (!usuarioNuevo.getPassword().equals(repetirPassword)){
				logger.error("Password ( "+usuarioNuevo.getPassword()+") y repetir password ("+repetirPassword+") no coinciden." );
				PrimeFaces.current().executeScript("error('Los campos de contraseña y repetir contraseña no coinciden.');");
				return;
			}
			
			if (usuarioNuevo.getEmail()!=null && !usuarioNuevo.getEmail().equals("")){
				if (!validaMail(usuarioNuevo.getEmail())){
					logger.error("Mail: "+usuarioNuevo.getEmail()+" no valida." );
					PrimeFaces.current().executeScript("error('El Mail no es válido, verifique nuevamente.');");
					return;
				}
			}

			usuarioNuevo.setPassword(getStringMessageDigest(usuarioNuevo.getPassword()));
			usuarioNuevo = ejbUsuarios.guardarUsuario(usuarioNuevo);
			
			sesion.setUsuarioLogueado(usuarioNuevo);
			
			logger.info("Se guardó usuario:" + usuarioNuevo.getId());

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			} catch (IOException e) {
				logger.error("Error al redirigir al login", e);
				PrimeFaces.current().executeScript("error('Ocurrió un error al redirigir. Vuelva a intentarlo.');");
				return;
			}
			return;

		} catch (Exception e) {
			logger.error("Error al registrar al usuario", e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al registrar al usuario. Vuelva a intentarlo más tarde.');");
		}
		return;
	}

	private String toHexadecimal(byte[] digest) {
		String hash = "";
		for (byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	private String getStringMessageDigest(String message) {
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			logger.error("Error creando Digest", ex);
			
		}
		return toHexadecimal(digest);
	}

	public void volver() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			return;
		} catch (IOException e) {
			logger.error("Error al redirigir al login", e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al redirigir. Vuelva a intentarlo.');");
			return;
		}

	}
	
	public static boolean validaMail(String mail) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+([\\-\\.][A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		boolean matchFound = false;
		if (mail == null || mail.equals("") || mail.equals(" ")) {
			return false;
		} else {
			try {
				final Pattern p = Pattern.compile(EMAIL_PATTERN);
				final Matcher m = p.matcher(mail.trim());
				matchFound = m.matches();
				if (!matchFound) {
					return false;
				}
			} catch (final Exception e) {
				return false;
			}
			if (!matchFound) {
				return false;
			}
		}
		return true;
	}

	public Usuario getUsuarioNuevo() {
		return usuarioNuevo;
	}

	public void setUsuarioNuevo(Usuario usuarioNuevo) {
		this.usuarioNuevo = usuarioNuevo;
	}

	public SesionBean getSssion() {
		return sesion;
	}

	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}

	public String getRepetirPassword() {
		return repetirPassword;
	}

	public void setRepetirPassword(String repetirPassword) {
		this.repetirPassword = repetirPassword;
	}

}
