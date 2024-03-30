package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.Fecha;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Partido;
import com.pronostico.penca.model.Pronostico;

@ManagedBean(name = "pronosticoUsuarioBean")
@ViewScoped
public class PronosticoUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@Inject
	ITorneoEJBLocal ejbTorneos;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;
	
	private String st;
	private int in;
	
	private Map<Long, Integer> mapPuntajes;
	private Map<Long, Integer> mapPuntajeFecha;
	private int puntajeTotalTorneo;
	
	private GrupoPenca grupoPencaUnica;
	
	final static Logger logger = Logger.getLogger(PronosticoUsuarioBean.class);
	
	@PostConstruct
	public void init() {
		logger.info("PronosticoUsuarioBean init()");
		try {
			mapPuntajes = new HashMap<Long, Integer>();
			mapPuntajeFecha = new HashMap<Long, Integer>();
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
//			GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(new Long(1));	
			GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(sesion.getGrupoPencaSeleccionado().getId());
			grupoPencaUnica = grupoPenca;
			puntajeTotalTorneo=0;
			for (Fecha fechas : sesion.getTorneoSeleccionado().getGrupos().get(0).getFechas()) {
				int acumuladoFecha=0;
				for (Partido partido : fechas.getPartidos()) {
					Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioSeleccioando(), grupoPenca, partido);
					if(pronostico!=null){
						partido.setGolesPronosticoLocal(pronostico.getGolesLocal());
						partido.setGolesPronosticoVisitante(pronostico.getGolesVisitante());
						if(partido.getGolesReglamentariosLocal()!=null && partido.getGolesReglamentariosVisitante()!=null){
							if(partido.getGolesReglamentariosLocal()==partido.getGolesPronosticoLocal() &&
									partido.getGolesReglamentariosVisitante()==partido.getGolesPronosticoVisitante()){
								pronostico.setPuntaje(5);
							} else if (partido.getGolesReglamentariosLocal()>partido.getGolesReglamentariosVisitante() &&
									partido.getGolesPronosticoLocal()>partido.getGolesPronosticoVisitante()){
								pronostico.setPuntaje(3);
							} else if (partido.getGolesReglamentariosLocal()<partido.getGolesReglamentariosVisitante() &&
									partido.getGolesPronosticoLocal()<partido.getGolesPronosticoVisitante()){
								pronostico.setPuntaje(3);
							} else if (partido.getGolesReglamentariosLocal()==partido.getGolesReglamentariosVisitante() &&
									partido.getGolesPronosticoLocal()==partido.getGolesPronosticoVisitante()){
								pronostico.setPuntaje(3);
							} else {
								pronostico.setPuntaje(0);
							}
						} else {
							pronostico.setPuntaje(0);
						}
						ejbTorneos.guardarPronostico(pronostico);
						mapPuntajes.put(partido.getId(), pronostico.getPuntaje());
						acumuladoFecha+=pronostico.getPuntaje();
						logger.info("Goles: " + partido.getGolesReglamentariosLocal() + " a " + partido.getGolesReglamentariosVisitante());
					}
				}
				mapPuntajeFecha.put(fechas.getId(), acumuladoFecha);
				puntajeTotalTorneo+=acumuladoFecha;
			}
		} catch (Exception e) {
			logger.error("Error al cargar los pronosticos del usuario: " + sesion.getUsuarioSeleccioando().getUsuario());
			PrimeFaces.current().executeScript("error('Ocurrió un error los pronósticos del usuario, vuelva a las posiciones.');");
		}
		
	}
	
	public Integer obtenerPronosticoPartidoUsuarioPencaLocal(Partido partido){
		try {
			Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioSeleccioando(), sesion.getGrupoPencaSeleccionado(), partido);
			return pronostico.getGolesLocal();
		} catch (Exception e) {
			logger.error("Error al obtener pronostico partido usuario local - partido: " + partido);
			return null;
		}
//		Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioSeleccioando(), sesion.getGrupoPencaSeleccionado(), partido);
//		return pronostico.getGolesLocal();
	}
	
	public Integer obtenerPronosticoPartidoUsuarioPencaVisitante(Partido partido){
		try {
			Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioSeleccioando(), sesion.getGrupoPencaSeleccionado(), partido);
			return pronostico.getGolesVisitante();
		} catch (Exception e) {
			logger.error("Error al obtener pronostico partido usuario visitante - partido: " + partido);
			return null;
		}
	}
	
	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean sesion) {
		this.sesion = sesion;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public int getIn() {
		return in;
	}

	public void setIn(int in) {
		this.in = in;
	}

	public Map<Long, Integer> getMapPuntajes() {
		return mapPuntajes;
	}

	public void setMapPuntajes(Map<Long, Integer> mapPuntajes) {
		this.mapPuntajes = mapPuntajes;
	}
	

	public Map<Long, Integer> getMapPuntajeFecha() {
		return mapPuntajeFecha;
	}

	public void setMapPuntajeFecha(Map<Long, Integer> mapPuntajeFecha) {
		this.mapPuntajeFecha = mapPuntajeFecha;
	}

	public int getPuntajeTotalTorneo() {
		return puntajeTotalTorneo;
	}

	public void setPuntajeTotalTorneo(int puntajeTotalTorneo) {
		this.puntajeTotalTorneo = puntajeTotalTorneo;
	}

	public GrupoPenca getGrupoPencaUnica() {
		return grupoPencaUnica;
	}

	public void setGrupoPencaUnica(GrupoPenca grupoPencaUnica) {
		this.grupoPencaUnica = grupoPencaUnica;
	}
	
}
