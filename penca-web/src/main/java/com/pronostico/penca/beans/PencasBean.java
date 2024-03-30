package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import com.pronostico.penca.DT.DTGrupoPenca;
import com.pronostico.penca.ejb.api.IGrupoPencaEJBLocal;
import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.enumerado.EnumEstadoGrupoPencauUsuario;
import com.pronostico.penca.enumerado.EnumEstadoSolicitudPenca;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.SolicitudPenca;

@ManagedBean(name = "pencasBean")
@ViewScoped
public class PencasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@Inject
	ITorneoEJBLocal ejbTorneos;
	
	@Inject
	IGrupoPencaEJBLocal ejbGrupoPenca;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;

	private String usuario;
	private String nombre;
	
	private List<GrupoPenca> grupoPencas;
	private List<GrupoPenca> grupoPencasUsuario;
	private List<SolicitudPenca> solicitudesPencasUsuario;
	private Long grupoPencaSeleccionado;
	
	private List<DTGrupoPenca> dtGrupoPencas;
	
	final static Logger logger = Logger.getLogger(PencasBean.class);
	
	@PostConstruct
	public void init() {
		logger.info("PencasBean init()");
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
			if (sesion.getUsuarioLogueado() != null) {
				//pencas del usuario
				grupoPencasUsuario = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioTorneo(sesion.getUsuarioLogueado().getId(), sesion.getTorneoSeleccionado().getId());
//				grupoPencasUsuario = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioTorneo(sesion.getUsuarioLogueado().getId(), sesion.getTorneoSeleccionado().getId());
				//todas las pencas
				grupoPencas = ejbGrupoPenca.obtenerTodosLosGruposPencasTorneo(sesion.getTorneoSeleccionado().getId());
				//solicitudes de pencas
				solicitudesPencasUsuario = ejbGrupoPenca.obtenerSolicitudesPencasDeUsuario(sesion.getUsuarioLogueado().getId());
				
				dtGrupoPencas = new ArrayList<DTGrupoPenca>();
				for (GrupoPenca grupoPenca : grupoPencas) {
					DTGrupoPenca dt = new DTGrupoPenca();
					dt.setGrupoPenca(grupoPenca);
					
					if (grupoPencasUsuario.contains(grupoPenca)){
						dt.setEstado(EnumEstadoGrupoPencauUsuario.PERTENECE);
					} else {
						boolean pendiente = false;
						for (SolicitudPenca sp : solicitudesPencasUsuario) {
							if (sp.getGrupoPenca().equals(grupoPenca)){
								dt.setEstado(EnumEstadoGrupoPencauUsuario.PENDIENTE);
								pendiente = true;
								break;
							}
						}
						if (!pendiente){
							dt.setEstado(EnumEstadoGrupoPencauUsuario.NO_PERTENECE);
						}
					}
					dtGrupoPencas.add(dt);
				}
				
			} 
		} catch (Exception e) {
			logger.error("Error al cargar las pencas del usuario: " + sesion.getUsuarioSeleccioando().getUsuario());
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar las pencas del usuario, vuelva a los torneos.');");
		}
		
	}
	
	public void solicitarAcceso(DTGrupoPenca dtGrupoPenca) {
		try {
			//Si es abierta entonces ya creo el grupo penca asociado al usuario
			if (dtGrupoPenca.getGrupoPenca().isEsAbierta()){
//			if (sesion.getUsuarioLogueado().getGrupoPencas()==null){
//				sesion.getUsuarioLogueado().setGrupoPencas(new ArrayList<GrupoPenca>());
//			}
//			sesion.getUsuarioLogueado().getGrupoPencas().size();
//			sesion.getUsuarioLogueado().getGrupoPencas().add(dtGrupoPenca.getGrupoPenca());
//			sesion.setUsuarioLogueado(ejbUsuarios.guardarUsuario(sesion.getUsuarioLogueado()));
				dtGrupoPenca.getGrupoPenca().getUsuarios().add(sesion.getUsuarioLogueado());
				ejbGrupoPenca.guardarGrupoPenca(dtGrupoPenca.getGrupoPenca());
			} else{
				//Si es cerrada creo la solicitud al grupo penca en pendiente, cuando el admin la acepte cambia de estado
				// y se agrega el grupo penca al usuario
				SolicitudPenca sp = new SolicitudPenca();
				sp.setEstado(EnumEstadoSolicitudPenca.PENDIENTE);
				sp.setGrupoPenca(dtGrupoPenca.getGrupoPenca());
				sp.setUsuario(sesion.getUsuarioLogueado());
				if (sesion.getUsuarioLogueado().getSolicitudesPencas()==null){
					sesion.getUsuarioLogueado().setSolicitudesPencas(new ArrayList<SolicitudPenca>());
				}
				sesion.getUsuarioLogueado().getSolicitudesPencas().size();
				sesion.getUsuarioLogueado().getSolicitudesPencas().add(sp);
				sesion.setUsuarioLogueado(ejbUsuarios.guardarUsuario(sesion.getUsuarioLogueado()));
			}
			//ejbGrupoPenca.guardarSolicitudPenca(sp);
			logger.info("Solicitud de acceso exitosa al grupo penca: " + dtGrupoPenca.getGrupoPenca().getNombre());
			// ********* ARREGLAR ESTO CON PRIMFACES Y ACTUALIZAR LA TABLA
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("pencas.jsf");
			} catch (IOException e) {
				logger.error("Error al redirigir");
				return;
			}
			
		} catch (Exception e) {
			logger.error("Error al solicitar acceso a la penca: " + sesion.getUsuarioSeleccioando().getUsuario());
			PrimeFaces.current().executeScript("error('Ocurrió un error al solicitar acceso, vuelva a los torneos.');");
		}
		return;
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

	public List<GrupoPenca> getGrupoPencasUsuario() {
		return grupoPencasUsuario;
	}

	public void setGrupoPencasUsuario(List<GrupoPenca> grupoPencasUsuario) {
		this.grupoPencasUsuario = grupoPencasUsuario;
	}

	public List<SolicitudPenca> getSolicitudesPencasUsuario() {
		return solicitudesPencasUsuario;
	}

	public void setSolicitudesPencasUsuario(List<SolicitudPenca> solicitudesPencasUsuario) {
		this.solicitudesPencasUsuario = solicitudesPencasUsuario;
	}

	public List<DTGrupoPenca> getDtGrupoPencas() {
		return dtGrupoPencas;
	}

	public void setDtGrupoPencas(List<DTGrupoPenca> dtGrupoPencas) {
		this.dtGrupoPencas = dtGrupoPencas;
	}
	
	
}
