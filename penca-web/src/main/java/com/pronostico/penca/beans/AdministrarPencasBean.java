package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.DT.DTGrupoPenca;
import com.pronostico.penca.ejb.api.IGrupoPencaEJBLocal;
import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.enumerado.EnumEstadoSolicitudPenca;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.SolicitudPenca;
import com.pronostico.penca.model.Usuario;

@ManagedBean(name = "administrarPencasBean")
@ViewScoped
public class AdministrarPencasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@Inject
	ITorneoEJBLocal ejbTorneos;
	
	@Inject
	IGrupoPencaEJBLocal ejbGrupoPenca;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;
	
	private String nombrePenca;
	private boolean publica;
	private boolean notificaciones;
	
	private List<GrupoPenca> grupoPencas;
	private List<GrupoPenca> grupoPencasUsuarioAdministrador;
	private List<SolicitudPenca> solicitudesPencasUsuarioAdministrador;
	private Long grupoPencaSeleccionado;
	
	private List<DTGrupoPenca> dtGrupoPencas;
	
	final static Logger logger = Logger.getLogger(AdministrarPencasBean.class);
	
	@PostConstruct
	public void init() {
		logger.info("AdministrarPencasBean init()");
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
			//pencas del usuario
			grupoPencasUsuarioAdministrador = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioAdministrador(sesion.getUsuarioLogueado().getId());
			
			//solicitudes de pencas
			solicitudesPencasUsuarioAdministrador = ejbGrupoPenca.obtenerSolicitudesPencasDeUsuarioAdministrador(sesion.getUsuarioLogueado().getId());
			
			
		} catch (Exception e) {
			logger.error("Error al cargar la página");
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar la penca, vuelva a seleccionar el torneo.');");
		}
		
	}
	
	public void crearPenca(){
		try {
			// controles
			if (nombrePenca==null || nombrePenca.trim().equals("")){
				logger.error("Nombre penca vacío");
				PrimeFaces.current().executeScript("error('Debe seleccionar nombre para la penca.');");
				return;
			}
			// cantidad de pencas de un usuario // VER QUE SEA POR TORNEO y que sean 3
			if (sesion.getUsuarioLogueado().getGrupoPencas()!=null && sesion.getUsuarioLogueado().getGrupoPencas().size()==10){
				logger.error("más de 10 pencas");
				PrimeFaces.current().executeScript("error('Llegó al máximo de pencas administradas, elimine alguna ya existente.');");
				return;
			}
			GrupoPenca gp = new GrupoPenca();
			gp.setNombre(nombrePenca);
			gp.setMandaNotificaciones(notificaciones);
			gp.setEsAbierta(publica);
			gp.setUsuarioAdministrador(sesion.getUsuarioLogueado());
			gp.setTorneo(sesion.getTorneoSeleccionado());
			List<Usuario> usuarios = new ArrayList<Usuario>();
			usuarios.add(sesion.getUsuarioLogueado());
			gp.setUsuarios(usuarios);
			ejbGrupoPenca.guardarGrupoPenca(gp);
			nombrePenca="";
			notificaciones = false;
			publica = false;
			//pencas del usuario
			grupoPencasUsuarioAdministrador = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioAdministrador(sesion.getUsuarioLogueado().getId());
			//solicitudes de pencas
			solicitudesPencasUsuarioAdministrador = ejbGrupoPenca.obtenerSolicitudesPencasDeUsuarioAdministrador(sesion.getUsuarioLogueado().getId());
			logger.info("Finalizo el creado de la penca "+nombrePenca);
			PrimeFaces.current().executeScript("guardarExito('Se crea la penca correctamente.');");
		} catch (Exception e) {
			logger.error("Error al cargar la penca");
			PrimeFaces.current().executeScript("error('Ocurrió un error al crear la penca, vuelva a seleccionar el torneo.');");
		}
	}
	
	public void aceptarSolicitud(SolicitudPenca s){
		try {
			s.setEstado(EnumEstadoSolicitudPenca.APROBADO);
			ejbGrupoPenca.guardarSolicitudPenca(s);
		} catch (Exception e) {
			logger.error("Error al aceptar la solicitud");
			PrimeFaces.current().executeScript("error('Ocurrió un error al aceptar la solicitud, vuelva a seleccionar el torneo.');");
		}
	}
	
	public String refresh() {
		return "";
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}

	public List<GrupoPenca> getGrupoPencas() {
		return grupoPencas;
	}

	public void setGrupoPencas(List<GrupoPenca> grupoPencas) {
		this.grupoPencas = grupoPencas;
	}

	public Long getGrupoPencaSeleccionado() {
		return grupoPencaSeleccionado;
	}

	public void setGrupoPencaSeleccionado(Long grupoPencaSeleccionado) {
		this.grupoPencaSeleccionado = grupoPencaSeleccionado;
	}

	public List<GrupoPenca> getGrupoPencasUsuarioAdministrador() {
		return grupoPencasUsuarioAdministrador;
	}

	public void setGrupoPencasUsuarioAdministrador(List<GrupoPenca> grupoPencasUsuarioAdministrador) {
		this.grupoPencasUsuarioAdministrador = grupoPencasUsuarioAdministrador;
	}

	public List<SolicitudPenca> getSolicitudesPencasUsuarioAdministrador() {
		return solicitudesPencasUsuarioAdministrador;
	}

	public void setSolicitudesPencasUsuarioAdministrador(List<SolicitudPenca> solicitudesPencasUsuarioAdministrador) {
		this.solicitudesPencasUsuarioAdministrador = solicitudesPencasUsuarioAdministrador;
	}

	public List<DTGrupoPenca> getDtGrupoPencas() {
		return dtGrupoPencas;
	}

	public void setDtGrupoPencas(List<DTGrupoPenca> dtGrupoPencas) {
		this.dtGrupoPencas = dtGrupoPencas;
	}

	public String getNombrePenca() {
		return nombrePenca;
	}

	public void setNombrePenca(String nombrePenca) {
		this.nombrePenca = nombrePenca;
	}

	public boolean isPublica() {
		return publica;
	}

	public void setPublica(boolean publica) {
		this.publica = publica;
	}

	public boolean isNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(boolean notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	
	
	
}
