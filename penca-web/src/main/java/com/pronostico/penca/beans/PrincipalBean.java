package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.Torneo;

@ManagedBean(name = "principalBean")
@RequestScoped
public class PrincipalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@Inject
	ITorneoEJBLocal ejbTorneos;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;

	private String usuario;
	private String nombre;

	private List<Torneo> torneos;
	private List<Grupo> grupos;
	
	final static Logger logger = Logger.getLogger(PrincipalBean.class);

	@PostConstruct
	public void init() {
		logger.info("PrincipalBean init()");
		try {
			if (sesion == null || sesion.getUsuarioLogueado() == null) {
				logger.info("usuario null");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			} 
			logger.info("Usuario logueado: " + sesion.getUsuarioLogueado().getUsuario());
			logger.info("PrincipalBean usaurio logueado: " + sesion.getUsuarioLogueado().getUsuario());
			torneos = ejbTorneos.obtenerTorneosVigentes();
			usuario = sesion.getUsuarioLogueado().getUsuario();
			nombre = sesion.getUsuarioLogueado().getNombre();
			sesion.setGrupoPencaUnica(null);
			
		} catch (Exception e) {
			logger.error("Error al cargar los torneos");
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar los torneos, vuelva loguearse.');");
		}
	}
	
	public void irPronosticar(Long idTorneo) {

		try {
			Torneo torneo = ejbTorneos.obtenerTorneoPorId(idTorneo);
			sesion.setTorneoSeleccionado(torneo);
			FacesContext.getCurrentInstance().getExternalContext().redirect("pronostico.jsf");
		} catch (IOException e) {
			logger.error("Error al ir a pronósticos - torneo: " +idTorneo);
			PrimeFaces.current().executeScript("error('Ocurrió un error al ir a los pronósticos, vuelva a loguearse.');");
			return;
		}
		return;
	}
	public void irPuntajes(Long idTorneo) {
		
		try {
			Torneo torneo = ejbTorneos.obtenerTorneoPorId(idTorneo);
			sesion.setTorneoSeleccionado(torneo);
			FacesContext.getCurrentInstance().getExternalContext().redirect("puntajes.jsf");
		} catch (IOException e) {
			logger.error("Error al ir a puntajes - torneo: " +idTorneo);
			PrimeFaces.current().executeScript("error('Ocurrió un error al ir a las posiciones, vuelva a loguearse.');");
			return;
		}
		return;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Torneo> getTorneos() {
		return this.torneos;
	}

	public void setTorneos(List<Torneo> torneos) {
		this.torneos = torneos;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	

}
