package com.pronostico.penca.ejb.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.pronostico.penca.ejb.api.IGrupoPencaEJBLocal;
import com.pronostico.penca.enumerado.EnumEstadoSolicitudPenca;
import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Pronostico;
import com.pronostico.penca.model.SolicitudPenca;
import com.pronostico.penca.model.Usuario;

/**
 * Session Bean implementation class GrupoEJB
 */
@Stateless
public class GrupoPencaEJB implements IGrupoPencaEJBLocal {

	Logger logger = Logger.getLogger("GrupoEJB");

	@PersistenceContext(unitName = "primary1")
	EntityManager em;

	public Grupo altaGrupo(Grupo g) {
		g = em.merge(g);

		return g;
	}

	public GrupoPenca obtenerGrupoPencaPorNombre(String nombreGrupo) {

		Query query = em.createNamedQuery("obtenerGrupoPencaPorNombre");
		query.setParameter("nombreGrupoPenca", nombreGrupo);

		GrupoPenca g = (GrupoPenca) query.getSingleResult();

		return g;

	}

	public GrupoPenca obtenerGrupoPencaPorId(Long idGrupo) {

		GrupoPenca g = em.find(GrupoPenca.class, idGrupo);

		return g;

	}

	public List<GrupoPenca> obtenerTodosLosGrupos() {
		Query query = em.createNamedQuery("obtenerGrupoPenca");

		List<GrupoPenca> grupos = query.getResultList();

		return grupos;

	}
	
	public List<GrupoPenca> obtenerTodosLosGruposPencasTorneo(Long idTorneo) {
		Query query = em.createNamedQuery("obtenerGrupoPencaTorneo");
		query.setParameter("idTorneo", idTorneo);
		List<GrupoPenca> grupos = query.getResultList();
		return grupos;

	}
	
	public List<Pronostico> obtenerPronosticosPorGrupoPenca(Long idGrupoPenca) {
		Query query = em.createNamedQuery("obtenerPronosticosPorGrupoPenca");

		List<Pronostico> pronosticosDelGrupo = query.getResultList();

		return pronosticosDelGrupo;

	}
	
	public List<GrupoPenca> obtenerGrupoPencasDeUsuario(Long idUsuario) {
		Query query = em.createNamedQuery("obtenerGruposPencasDeUsuario");

		query.setParameter("idUsuario", idUsuario);
		
		List<GrupoPenca> prugpoPencas = query.getResultList();

		return prugpoPencas;

	}
	
	public List<GrupoPenca> obtenerGrupoPencasDeUsuarioTorneo(Long idUsuario, Long idTorneo) {
		Query query = em.createNamedQuery("obtenerGruposPencasDeUsuarioTorneo");

		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idTorneo", idTorneo);
		
		List<GrupoPenca> prugpoPencas = query.getResultList();

		return prugpoPencas;

	}
	
	public List<GrupoPenca> obtenerGrupoPencasDeUsuarioAdministrador(Long idUsuario) {
		Query query = em.createNamedQuery("obtenerGrupoPencasUsuarioAdminitrador");

		query.setParameter("idUsuario", idUsuario);
		
		List<GrupoPenca> grupoPencas = query.getResultList();

		return grupoPencas;

	}
	
	public List<SolicitudPenca> obtenerSolicitudesPencasDeUsuario(Long idUsuario) {
		Query query = em.createNamedQuery("obtenerSolicitudesPencaDeUsuario");

		query.setParameter("idUsuario", idUsuario);
		
		List<SolicitudPenca> solicitudesPencas = query.getResultList();

		return solicitudesPencas;

	}
	
	public List<SolicitudPenca> obtenerSolicitudesPencasDeUsuarioAdministrador(Long idUsuario) {
		Query query = em.createNamedQuery("obtenerSolicitudesPencaDeUsuarioAdministrador");

		query.setParameter("idUsuario", idUsuario);
		query.setParameter("estado", EnumEstadoSolicitudPenca.PENDIENTE);
		
		List<SolicitudPenca> solicitudesPencas = query.getResultList();

		return solicitudesPencas;

	}
	


	public GrupoPenca obtenerGrupoPencaPorHash(Long hash) {
		// Obtengo el ID del grupoPenca a partir del hash
		GrupoPenca g = em.find(GrupoPenca.class, hash);

		return g;

	}
	
	public Long obtenerHashDeGrupoPenca(Long id) {
		
		Long hash = null;
		//Obtengo el hash a partir del id de grupo
		
		
		return hash;
	}
	
	public void enviarMensajeAlGrupo(Long idGrupo) {
		
		GrupoPenca g = obtenerGrupoPencaPorId(idGrupo);
		
		List<Usuario> usuariosGrupo = g.getUsuarios();
		for (Usuario usuario : usuariosGrupo) {
			//Envio mail
			logger.info("Direccion de mail:" + usuario.getEmail());
		}
		
	}
	
	public List<String> obtenerTablaDelGrupo(Long idGrupoPenca) {
		//FIXME
		//Recorro la lista de usuarios y calculo sus puntajes?
		//Mejor definir un DTO con la tabla de puntajes, no?
		
		return null;
	}
	
	public List<String> obtenerTablaGeneral() {
		//FIXME
		//Recorro la lista de usuarios y calculo sus puntajes, tomando el mejor de ellos para la general?
		//Mejor definir un DTO con la tabla de puntajes, no?
		
		return null;
	}
	
	public SolicitudPenca guardarSolicitudPenca(SolicitudPenca sp) {
		SolicitudPenca sp2 = em.merge(sp);
		return sp2;
	}
	
	public GrupoPenca guardarGrupoPenca(GrupoPenca gp) {
		GrupoPenca gp2 = em.merge(gp);
		return gp2;
	}

}
