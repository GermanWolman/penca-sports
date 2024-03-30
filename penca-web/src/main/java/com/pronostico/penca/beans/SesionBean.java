package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Torneo;
import com.pronostico.penca.model.Usuario;

@ManagedBean(name = "sesionBean")
@SessionScoped
public class SesionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogueado;
	private Usuario usuarioSeleccioando;

	private boolean estaLogueado;
	
	private Torneo torneoSeleccionado;
	
	private GrupoPenca grupoPencaSeleccionado;
	
	private int intervalMaxSession;
	
	private GrupoPenca grupoPencaUnica;
	
	final static Logger logger = Logger.getLogger(PencasBean.class);

	@PostConstruct
	public void init() {
		System.out.println("init sesionBean");
//		usuarioLogueado = null;
//		estaLogueado = false;
		if (usuarioLogueado == null) {
			logger.info("usuario null");
			try {
	              FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
	              
	        } catch (IOException e1) {
	              e1.printStackTrace();
	        }
			return;
		} 
		logger.info("Usuario logueado: " + usuarioLogueado.getUsuario());

	}

	public void logout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		usuarioLogueado = null;
		this.estaLogueado = false;
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
		} catch (IOException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cerrar cesion", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		return;
	}


	public Usuario getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public boolean isEstaLogueado() {
		return estaLogueado;
	}

	public void setEstaLogueado(boolean estaLogueado) {
		this.estaLogueado = estaLogueado;
	}

	public Torneo getTorneoSeleccionado() {
		return torneoSeleccionado;
	}

	public void setTorneoSeleccionado(Torneo torneoSeleccionado) {
		this.torneoSeleccionado = torneoSeleccionado;
	}

	public Usuario getUsuarioSeleccioando() {
		return usuarioSeleccioando;
	}

	public void setUsuarioSeleccioando(Usuario usuarioSeleccioando) {
		this.usuarioSeleccioando = usuarioSeleccioando;
	}

	public GrupoPenca getGrupoPencaSeleccionado() {
		return grupoPencaSeleccionado;
	}

	public void setGrupoPencaSeleccionado(GrupoPenca grupoPencaSeleccionado) {
		this.grupoPencaSeleccionado = grupoPencaSeleccionado;
	}

	public int getIntervalMaxSession() {
		return intervalMaxSession;
	}

	public void setIntervalMaxSession(int intervalMaxSession) {
		this.intervalMaxSession = intervalMaxSession;
	}

	public GrupoPenca getGrupoPencaUnica() {
		return grupoPencaUnica;
	}

	public void setGrupoPencaUnica(GrupoPenca grupoPencaUnica) {
		this.grupoPencaUnica = grupoPencaUnica;
	}
	
}
