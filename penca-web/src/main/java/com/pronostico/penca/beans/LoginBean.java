package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.Usuario;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;

	private String usuario;
	private String password;
	
	final static Logger logger = Logger.getLogger(LoginBean.class);

	@PostConstruct
	public void init() {
		try {
			logger.info("Login Bean init()");
			
		} catch (Exception e) {
			logger.error("Error al cargar login", e);
			PrimeFaces.current().executeScript("error('Sistema en mantenimiento. Vuelva más tarde.');");
		}
	}

	public void loguear() {
		try {
			logger.info("loguear()---> usuario:" + usuario + " - password:" + password);
			if (usuario==null || usuario.trim().equals("")){
				logger.error("Usuario vacío");
				PrimeFaces.current().executeScript("error('Debe ingresar usuario.');");
				return;
			}
			if (password==null || password.trim().equals("")){
				logger.error("Password vacío");
				PrimeFaces.current().executeScript("error('Debe ingresar contraseña.');");
				return;
			}
			String con = "";
			con = getStringMessageDigest(password);
			logger.info("password cifrada:" + con);
			sesion.setUsuarioLogueado(null);
			Usuario u = ejbUsuarios.obtenerUsuario(usuario, con);
			if (null != u) {
				sesion.setUsuarioLogueado(u);
				sesion.setEstaLogueado(true);
				try {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("principal.jsf");
				} catch (IOException e) {
					logger.info("Error al redirigir", e);
					return;
				}
				return;
			} else {
				logger.info("Error de usuairo y/o contraseña");
				PrimeFaces.current().executeScript("error('Usuario y/o contraseña incorrecto. Vuelva a intentarlo.');");
				return;
			}

		} catch (Exception e) {
			logger.error("Error al crear usuario y contraseña", e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al verificar usuario y contraseña. Vuelva a intentarlo.');");
		}
		return;
	}

	public void irRegistrarUsuario() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("registro.jsf");
		} catch (IOException e) {
			logger.error("Error al ir a registrar usuario", e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al redirigir al registro. Vuelva a intentarlo más tarde.');");
			return;
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
			logger.error("Error creando Digest");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hubo un error en la aplicación.", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return toHexadecimal(digest);
	}

	public String redirigir(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url + ".jsf");
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al acceder", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "";
		}
		return "";

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}

}
