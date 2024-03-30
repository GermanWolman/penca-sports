package com.pronostico.penca.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.pronostico.penca.DT.DTUsuarioPuntaje;
import com.pronostico.penca.ejb.api.IGrupoPencaEJBLocal;
import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Pronostico;
import com.pronostico.penca.model.Usuario;

@ManagedBean(name = "puntajesBean")
@ViewScoped
public class PuntajesBean implements Serializable {

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

	private List<DTUsuarioPuntaje> dtUsuariosPuntajes;
	
	private List<GrupoPenca> grupoPencas;
	private Long grupoPencaSeleccionado;
	
	private GrupoPenca grupoPencaUnica;
	
	final static Logger logger = Logger.getLogger(PuntajesBean.class);
	
	@PostConstruct
	public void init() {
		logger.info("PuntajesBean init()");
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
				dtUsuariosPuntajes = new ArrayList<DTUsuarioPuntaje>();
				grupoPencas = ejbGrupoPenca.obtenerGrupoPencasDeUsuarioTorneo(sesion.getUsuarioLogueado().getId(), sesion.getTorneoSeleccionado().getId());
				if (grupoPencas==null || grupoPencas.size()==0){
					System.err.println("Usuario no pertenece a ninguna penca: " + sesion.getUsuarioLogueado().getId());
				} else{
					if (sesion.getGrupoPencaUnica()!=null){
						//GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(sesion.getGrupoPencaUnica().getId());	
						grupoPencaSeleccionado = sesion.getGrupoPencaUnica().getId();
						//grupoPencaUnica = sesion.getGrupoPencaUnica();
						for (Usuario usuario : sesion.getGrupoPencaUnica().getUsuarios()) {
							List<Pronostico> pronosticosUsuario = ejbTorneos.obtenerPronosticosUsuario(usuario, sesion.getGrupoPencaUnica());
							DTUsuarioPuntaje dtUsuarioPuntaje = new DTUsuarioPuntaje();
							dtUsuarioPuntaje.setUsuario(usuario);
							dtUsuarioPuntaje.setGrupoPenca(sesion.getGrupoPencaUnica());
							dtUsuarioPuntaje.setTorneo(sesion.getTorneoSeleccionado());
							int puntaje=0;
							for (Pronostico pronostico : pronosticosUsuario) {
								puntaje+=pronostico.getPuntaje();
							}
							dtUsuarioPuntaje.setPuuntaje(puntaje);
							dtUsuariosPuntajes.add(dtUsuarioPuntaje);
						}
						Collections.sort(dtUsuariosPuntajes, new Comparator<DTUsuarioPuntaje>() {
						    @Override
						    public int compare(DTUsuarioPuntaje o1, DTUsuarioPuntaje o2) {
						    	if(o1.getPuuntaje() < o2.getPuuntaje()){
						    		return 1;
						    	}
						    	if(o1.getPuuntaje() > o2.getPuuntaje()){
						    		return -1;
						    	}
						    	return 0;
						    }
						});
					}
					
				}
				
				
			}
		} catch (Exception e) {
			logger.error("Error al cargar las posiciones");
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar las posiciones, vuelva a seleccionar el torneo.');");
		}
		
	}
	
	public void grupoPencaChange(ValueChangeEvent e){
		try {
			grupoPencaSeleccionado = (Long) e.getNewValue();
//			grupoPencas = ejbGrupoPenca.obtenerGrupoPencaDeUsuario(sesion.getUsuarioLogueado().getId());
			GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(grupoPencaSeleccionado);	
			sesion.setGrupoPencaUnica(grupoPenca);	
			if (sesion.getUsuarioLogueado() != null) {
				dtUsuariosPuntajes = new ArrayList<DTUsuarioPuntaje>();
//				grupoPenca = ejbTorneos.obtenerGrupoPencaPorIdTorneo(sesion.getTorneoSeleccionado().getId());
				for (Usuario usuario : grupoPenca.getUsuarios()) {
					List<Pronostico> pronosticosUsuario = ejbTorneos.obtenerPronosticosUsuario(usuario, grupoPenca);
					DTUsuarioPuntaje dtUsuarioPuntaje = new DTUsuarioPuntaje();
					dtUsuarioPuntaje.setUsuario(usuario);
					dtUsuarioPuntaje.setGrupoPenca(grupoPenca);
					dtUsuarioPuntaje.setTorneo(sesion.getTorneoSeleccionado());
					int puntaje=0;
					for (Pronostico pronostico : pronosticosUsuario) {
						puntaje+=pronostico.getPuntaje();
					}
					dtUsuarioPuntaje.setPuuntaje(puntaje);
					dtUsuariosPuntajes.add(dtUsuarioPuntaje);
				}
				Collections.sort(dtUsuariosPuntajes, new Comparator<DTUsuarioPuntaje>() {
				    @Override
				    public int compare(DTUsuarioPuntaje o1, DTUsuarioPuntaje o2) {
				    	if(o1.getPuuntaje() < o2.getPuuntaje()){
				    		return 1;
				    	}
				    	if(o1.getPuuntaje() > o2.getPuuntaje()){
				    		return -1;
				    	}
				    	return 0;
				    }
				});
				logger.info("Finalizo el cambio de la penca");
			} else {
				logger.info("usuario null");
				//redirigir al login
			}
		} catch (Exception e1) {
			logger.error("Error al cambiar penca - Error: " + e);
			PrimeFaces.current().executeScript("error('Ocurrió un error al cargar los datos de la penca, vuelva a seleccionar el torneo.');");
		}
	}
	
	public void irPronosticos(Long idUsuario) {

		try {
			Usuario usuario = ejbTorneos.obtenerUsuarioPorId(idUsuario);
			sesion.setUsuarioSeleccioando(usuario);
			GrupoPenca grupoPenca = ejbTorneos.obtenerGrupoPencaPorId(grupoPencaSeleccionado);	
			sesion.setGrupoPencaSeleccionado(grupoPenca);
			FacesContext.getCurrentInstance().getExternalContext().redirect("pronosticoUsuario.jsf");
		} catch (IOException e) {
			logger.error("Error al ir a pronósticos - usuario: " +idUsuario);
			PrimeFaces.current().executeScript("error('Ocurrió un error al ir a los pronósticos del usuario, vuelva a seleccionar el torneo.');");
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

	public List<DTUsuarioPuntaje> getDtUsuariosPuntajes() {
		return dtUsuariosPuntajes;
	}

	public void setDtUsuariosPuntajes(List<DTUsuarioPuntaje> dtUsuariosPuntajes) {
		this.dtUsuariosPuntajes = dtUsuariosPuntajes;
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

	public GrupoPenca getGrupoPencaUnica() {
		return grupoPencaUnica;
	}

	public void setGrupoPencaUnica(GrupoPenca grupoPencaUnica) {
		this.grupoPencaUnica = grupoPencaUnica;
	}
	
}
