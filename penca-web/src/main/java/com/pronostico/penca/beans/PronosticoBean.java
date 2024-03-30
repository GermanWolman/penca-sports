package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import com.pronostico.penca.ejb.api.IGrupoPencaEJBLocal;
import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.Fecha;
import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Partido;
import com.pronostico.penca.model.Pronostico;


import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "pronosticoBean")
@ViewScoped
public class PronosticoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	IUsuariosEjbLocal ejbUsuarios;

	@Inject
	ITorneoEJBLocal ejbTorneos;

	@Inject
	IGrupoPencaEJBLocal ejbGrupoPenca;

	@ManagedProperty("#{sesionBean}")
	private SesionBean sesion;

	private String st;
	private int in;

	private Map<Long, Integer> mapPuntajes;
	private Map<Long, Integer> mapPuntajeFecha;
	private int puntajeTotalTorneo;

	private List<GrupoPenca> grupoPencas;
	private Long grupoPencaSeleccionado;

	// private GrupoPenca grupoPencaUnica;

	final static Logger logger = Logger.getLogger(PronosticoBean.class);

	@PostConstruct
	public void init() {
		logger.info("PronosticoBean init()");
		mapPuntajes = new HashMap<Long, Integer>();
		mapPuntajeFecha = new HashMap<Long, Integer>();
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
			grupoPencas = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioTorneo(sesion.getUsuarioLogueado().getId(),
					sesion.getTorneoSeleccionado().getId());
			// grupoPencas =
			// ejbGrupoPenca.obtenerGrupoPencasDeUsuario(sesion.getUsuarioLogueado().getId());
			// grupoPencaUnica=null;
			if (grupoPencas == null || grupoPencas.size() == 0) {
				System.err.println("Usuario no pertenece a ninguna penca: " + sesion.getUsuarioLogueado().getId());
			} else{
				if (sesion.getGrupoPencaUnica()!=null){
					grupoPencaSeleccionado = sesion.getGrupoPencaUnica().getId();
					puntajeTotalTorneo = 0;
					//List<Grupo> gruposTorneo = ejbTorneos.obtenerGruposTorneo(sesion.getTorneoSeleccionado());
					for (Grupo grupo : sesion.getTorneoSeleccionado().getGrupos()) {
						for (Fecha fechas : grupo.getFechas()) {
							int acumuladoFecha = 0;
							for (Partido partido : fechas.getPartidos()) {
								Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioLogueado(),
										sesion.getGrupoPencaUnica(), partido);
								if (pronostico != null) {
									partido.setGolesPronosticoLocal(pronostico.getGolesLocal());
									partido.setGolesPronosticoVisitante(pronostico.getGolesVisitante());
									if (partido.getGolesReglamentariosLocal() != null
											&& partido.getGolesReglamentariosVisitante() != null) {
										if (partido.getGolesReglamentariosLocal() == partido.getGolesPronosticoLocal()
												&& partido.getGolesReglamentariosVisitante() == partido
														.getGolesPronosticoVisitante()) {
											pronostico.setPuntaje(5);
										} else if (partido.getGolesReglamentariosLocal() > partido
												.getGolesReglamentariosVisitante()
												&& partido.getGolesPronosticoLocal() > partido.getGolesPronosticoVisitante()) {
											pronostico.setPuntaje(3);
										} else if (partido.getGolesReglamentariosLocal() < partido
												.getGolesReglamentariosVisitante()
												&& partido.getGolesPronosticoLocal() < partido.getGolesPronosticoVisitante()) {
											pronostico.setPuntaje(3);
										} else if (partido.getGolesReglamentariosLocal() == partido
												.getGolesReglamentariosVisitante()
												&& partido.getGolesPronosticoLocal() == partido.getGolesPronosticoVisitante()) {
											pronostico.setPuntaje(3);
										} else {
											pronostico.setPuntaje(0);
										}
									} else {
										pronostico.setPuntaje(0);
									}
									ejbTorneos.guardarPronostico(pronostico);
									mapPuntajes.put(partido.getId(), pronostico.getPuntaje());
									acumuladoFecha += pronostico.getPuntaje();
									logger.info("Goles: " + partido.getGolesReglamentariosLocal() + " a "
											+ partido.getGolesReglamentariosVisitante());
								} else{
									logger.info("No tiene pronostico - partido: " + partido.getId());
									partido.setGolesPronosticoLocal(null);
									partido.setGolesPronosticoVisitante(null);
									mapPuntajes.put(partido.getId(), 0);
								}
							}
						mapPuntajeFecha.put(fechas.getId(), acumuladoFecha);
						puntajeTotalTorneo += acumuladoFecha;
						}
					}
				}
				
			}/*
				 * else{ GrupoPenca grupoPenca =
				 * ejbTorneos.obtenerGrupoPencaPorId(grupoPencas.get(0).getId())
				 * ; grupoPencaSeleccionado = grupoPencas.get(0).getId();
				 * grupoPencaUnica = grupoPencas.get(0); puntajeTotalTorneo=0;
				 * for (Fecha fechas :
				 * sesion.getTorneoSeleccionado().getGrupos().get(0).getFechas()
				 * ) { int acumuladoFecha=0; for (Partido partido :
				 * fechas.getPartidos()) { Pronostico pronostico =
				 * ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.
				 * getUsuarioLogueado(), grupoPenca, partido);
				 * if(pronostico!=null){
				 * partido.setGolesPronosticoLocal(pronostico.getGolesLocal());
				 * partido.setGolesPronosticoVisitante(pronostico.
				 * getGolesVisitante());
				 * if(partido.getGolesReglamentariosLocal()!=null &&
				 * partido.getGolesReglamentariosVisitante()!=null){
				 * if(partido.getGolesReglamentariosLocal()==partido.
				 * getGolesPronosticoLocal() &&
				 * partido.getGolesReglamentariosVisitante()==partido.
				 * getGolesPronosticoVisitante()){ pronostico.setPuntaje(5); }
				 * else if (partido.getGolesReglamentariosLocal()>partido.
				 * getGolesReglamentariosVisitante() &&
				 * partido.getGolesPronosticoLocal()>partido.
				 * getGolesPronosticoVisitante()){ pronostico.setPuntaje(3); }
				 * else if (partido.getGolesReglamentariosLocal()<partido.
				 * getGolesReglamentariosVisitante() &&
				 * partido.getGolesPronosticoLocal()<partido.
				 * getGolesPronosticoVisitante()){ pronostico.setPuntaje(3); }
				 * else if (partido.getGolesReglamentariosLocal()==partido.
				 * getGolesReglamentariosVisitante() &&
				 * partido.getGolesPronosticoLocal()==partido.
				 * getGolesPronosticoVisitante()){ pronostico.setPuntaje(3); }
				 * else { pronostico.setPuntaje(0); } } else {
				 * pronostico.setPuntaje(0); }
				 * ejbTorneos.guardarPronostico(pronostico);
				 * mapPuntajes.put(partido.getId(), pronostico.getPuntaje());
				 * acumuladoFecha+=pronostico.getPuntaje(); logger.info(
				 * "Goles: " + partido.getGolesReglamentariosLocal() + " a " +
				 * partido.getGolesReglamentariosVisitante()); } }
				 * mapPuntajeFecha.put(fechas.getId(), acumuladoFecha);
				 * puntajeTotalTorneo+=acumuladoFecha; } }
				 */
		} catch (Exception e) {
			logger.error("Error al cargar los pronosticos");
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar los datos de la penca, vuelva a seleccionar el torneo.');");
		}

	}
	
	public void cambiarPenca() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String id = params.get("grupopenca");
			grupoPencaSeleccionado = (Long) Long.parseLong(id);
			// grupoPencas =
			// ejbGrupoPenca.obtenerGrupoPencaDeUsuario(sesion.getUsuarioLogueado().getId());
			sesion.setGrupoPencaUnica(ejbTorneos.obtenerGrupoPencaPorId(grupoPencaSeleccionado));
			puntajeTotalTorneo = 0;
			//List<Grupo> gruposTorneo = ejbTorneos.obtenerGruposTorneo(sesion.getTorneoSeleccionado());
			for (Grupo grupo : sesion.getTorneoSeleccionado().getGrupos()) {
				for (Fecha fechas : grupo.getFechas()) {
					int acumuladoFecha = 0;
					for (Partido partido : fechas.getPartidos()) {
						Pronostico pronostico = ejbTorneos.obtenerPronosticoUsuarioPartido(sesion.getUsuarioLogueado(),
								sesion.getGrupoPencaUnica(), partido);
						if (pronostico != null) {
							partido.setGolesPronosticoLocal(pronostico.getGolesLocal());
							partido.setGolesPronosticoVisitante(pronostico.getGolesVisitante());
							if (partido.getGolesReglamentariosLocal() != null
									&& partido.getGolesReglamentariosVisitante() != null) {
								if (partido.getGolesReglamentariosLocal() == partido.getGolesPronosticoLocal()
										&& partido.getGolesReglamentariosVisitante() == partido
												.getGolesPronosticoVisitante()) {
									pronostico.setPuntaje(5);
								} else if (partido.getGolesReglamentariosLocal() > partido
										.getGolesReglamentariosVisitante()
										&& partido.getGolesPronosticoLocal() > partido.getGolesPronosticoVisitante()) {
									pronostico.setPuntaje(3);
								} else if (partido.getGolesReglamentariosLocal() < partido
										.getGolesReglamentariosVisitante()
										&& partido.getGolesPronosticoLocal() < partido.getGolesPronosticoVisitante()) {
									pronostico.setPuntaje(3);
								} else if (partido.getGolesReglamentariosLocal() == partido
										.getGolesReglamentariosVisitante()
										&& partido.getGolesPronosticoLocal() == partido.getGolesPronosticoVisitante()) {
									pronostico.setPuntaje(3);
								} else {
									pronostico.setPuntaje(0);
								}
							} else {
								pronostico.setPuntaje(0);
							}
							ejbTorneos.guardarPronostico(pronostico);
							mapPuntajes.put(partido.getId(), pronostico.getPuntaje());
							acumuladoFecha += pronostico.getPuntaje();
							logger.info("Goles: " + partido.getGolesReglamentariosLocal() + " a "
									+ partido.getGolesReglamentariosVisitante());
						} else{
							logger.info("No tiene pronostico - partido: " + partido.getId());
							partido.setGolesPronosticoLocal(null);
							partido.setGolesPronosticoVisitante(null);
							mapPuntajes.put(partido.getId(), 0);
						}
					}
				mapPuntajeFecha.put(fechas.getId(), acumuladoFecha);
				puntajeTotalTorneo += acumuladoFecha;
				}
			}
			logger.info("Finalizo el cambio de la penca");
			//PrimeFaces.current().executeScript("guardarExito('Los pronósticos se ha guardado correctamente.');");
			
		} catch (Exception e) {
			logger.error("Error al cambiar penca - Error: " + e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar los datos de la penca, vuelva a seleccionar el torneo.');");
		}
	}

	public void guardarPronosticos(Long idFecha) {
		// Fecha fecha = ejbTorneos.obtenerFechaPorId(idFecha);
		try {
			//GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(grupoPencaSeleccionado);
//			List<Grupo> gruposTorneo = ejbTorneos.obtenerGruposTorneo(sesion.getTorneoSeleccionado());
			if (sesion.getTorneoSeleccionado().getGrupos()!=null){
				logger.info("Grupos del torneo: " + sesion.getTorneoSeleccionado().getGrupos().size());
			}
			for (Grupo g : sesion.getTorneoSeleccionado().getGrupos()) {
				for (Fecha f : g.getFechas()) {
					if (f.getId() == idFecha) {
						for (Partido p : f.getPartidos()) {

							ejbTorneos.guardarPronosticoPartido(p, sesion.getUsuarioLogueado(), sesion.getGrupoPencaUnica());
						}
						break;
					}
				}
			}
			init();
			logger.info("Finalizo el guardar pronosticos para la fecha "+idFecha);
			PrimeFaces.current().executeScript("guardarExito('Los pronósticos se han guardado correctamente.');");
			
		} catch (Exception e) {
			logger.error("Error al guardar los pronósticos de fecha: " + idFecha + " - Error: " + e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al guardar la penca, vuelva a seleccionar el torneo.');");
		}

	}
	
	public void nada (){
		logger.info("Ejecuto nada");
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

}
