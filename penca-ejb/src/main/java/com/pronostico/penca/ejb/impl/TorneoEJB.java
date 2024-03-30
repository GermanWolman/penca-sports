package com.pronostico.penca.ejb.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.pronostico.penca.ejb.api.ITorneoEJBLocal;
import com.pronostico.penca.model.Equipo;
import com.pronostico.penca.model.Fecha;
import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Partido;
import com.pronostico.penca.model.Pronostico;
import com.pronostico.penca.model.Torneo;
import com.pronostico.penca.model.Usuario;

/**
 * Session Bean implementation class TorneoEJB
 */
@Stateless
@LocalBean
@SuppressWarnings("unchecked")
public class TorneoEJB implements ITorneoEJBLocal {

	Logger logger = Logger.getLogger("TorneoEJB");

	@PersistenceContext(unitName = "primary1")
	EntityManager em;

	@Override
	public Torneo altaTorneo(Torneo torneo) {

		torneo = em.merge(torneo);

		return torneo;

	}

	@Override
	public Torneo obtenerTorneoPorId(Long id) {
		Torneo t = em.find(Torneo.class, id);
		if (t != null && t.getGrupos() != null) {
			logger.info("Traje " + t.getGrupos().size() + " equipos.");
		}
		return t;
	}
	
	@Override
	public Usuario obtenerUsuarioPorId(Long id) {
		Usuario u = em.find(Usuario.class, id);
		return u;
	}

	@Override
	public List<Torneo> obtenerTorneosVigentes() {

		List<Torneo> torneos = em.createNamedQuery("obtenerTorneos").getResultList();

		logger.info("Traje " + torneos.size() + " torneos.");

		for (Torneo torneo : torneos) {
			logger.info("Este torneo tiene" + torneo.getEquipos().size() + " equipos.");
		}

		return torneos;

	}

	@Override
	public Fecha obtenerFechaPorId(Long id) {
		Fecha f = em.find(Fecha.class, id);
		return f;
	}

	@Override
	public GrupoPenca obtenerGrupoPencaPorId(Long id) {
		GrupoPenca g = em.find(GrupoPenca.class, id);
		return g;
	}
	
//	@Override
//	public GrupoPenca obtenerGrupoPencaPorId(Long id) {
//		GrupoPenca g = em.find(GrupoPenca.class, id);
//		return g;
//	}

	@Override
	public void guardarPronosticoPartido(Partido p, Usuario u, GrupoPenca gp) {
		try {
			if (p.getGolesPronosticoLocal()!=null && p.getGolesPronosticoVisitante()!=null){
				Query query = em.createNamedQuery("obtenerPronosticoUsuarioPartidoGrupoPenca");
				query.setParameter("partido", p.getId()).setParameter("gp", gp.getId()).setParameter("usuario", u.getId());
	
				Pronostico pronostico = (Pronostico) query.getSingleResult();
				if (pronostico == null) {
					pronostico = new Pronostico();
	//				GrupoPenca grp = em.find(GrupoPenca.class, gp.getId());
					pronostico.setGrupoPenca(gp);
					pronostico.setUsuario(u);
					pronostico.setPartido(p);
				}
			
				pronostico.setGolesLocal(p.getGolesPronosticoLocal());
				pronostico.setGolesVisitante(p.getGolesPronosticoVisitante());
				if (p.getGolesPronosticoLocal().compareTo(p.getGolesPronosticoVisitante()) == 1) {
					pronostico.setEquipoGanador(p.getEquipoLocal());
				} else if (p.getGolesPronosticoLocal().compareTo(p.getGolesPronosticoVisitante()) == -1) {
					pronostico.setEquipoGanador(p.getEquipoLocal());
				}
				em.merge(pronostico);
			} else{
				System.out.println("no puso resultado para el partido " + p.getAlias());
			}

		} catch (NoResultException ne) {
			logger.info("No hay pronostico: " + p.getId());
			Pronostico pronostico = new Pronostico();
//			GrupoPenca grp = em.find(GrupoPenca.class, new Long(1));
			pronostico.setGrupoPenca(gp);
			pronostico.setUsuario(u);
			pronostico.setPartido(p);
			pronostico.setGolesLocal(p.getGolesPronosticoLocal());
			pronostico.setGolesVisitante(p.getGolesPronosticoVisitante());
			if (p.getGolesPronosticoLocal().compareTo(p.getGolesPronosticoVisitante()) == 1) {
				pronostico.setEquipoGanador(p.getEquipoLocal());
			} else if (p.getGolesPronosticoLocal().compareTo(p.getGolesPronosticoVisitante()) == -1) {
				pronostico.setEquipoGanador(p.getEquipoLocal());
			}
			em.merge(pronostico);

		} catch (Exception e) {
			logger.fine("Erro al obtener pronostico: " + p.getId());
		}
	}

	@Override
	public List<Pronostico> obtenerPronosticosUsuario(Usuario u, GrupoPenca g) {
		try {
			Query query = em.createNamedQuery("obtenerPronosticosUsuarioGrupoPenca");
			query.setParameter("gp", g.getId()).setParameter("usuario", u.getId());

			List<Pronostico> pronosticos = (List<Pronostico>) query.getResultList();
			return pronosticos;
		} catch (NoResultException ne) {
			logger.fine("No hay pronostico: usuario: " + u.getId());
		} catch (Exception e) {
			logger.fine("Error al obtener pronostico: usuario: " + u.getId());
		}
		return null;
	}

	@Override
	public Pronostico obtenerPronosticoUsuarioPartido(Usuario u, GrupoPenca g, Partido p) {
		try {
			Query query = em.createNamedQuery("obtenerPronosticoUsuarioPartidoGrupoPenca");
			query.setParameter("gp", g.getId()).setParameter("usuario", u.getId()).setParameter("partido", p.getId());

			Pronostico pronostico = (Pronostico) query.getSingleResult();
			return pronostico;
		} catch (NoResultException ne) {
			logger.fine("No hay pronostico: usuario: " + u.getId() + " para partido: " + p.getId());
		} catch (Exception e) {
			logger.fine("Error al obtener pronostico: usuario: " + u.getId());
		}
		return null;
	}

	@Override
	public List<Equipo> guardarEquipos(List<Equipo> equipos) {
		for (Equipo equipo : equipos) {
			equipo = em.merge(equipo);
			logger.fine("Equipo guardado:" + equipo);
		}

		return equipos;
	}

	@Override
	public void guardarPronostico(Pronostico p) {
		p = em.merge(p);
		logger.info("Se guardó el pronostico id: " + p.getId());
	}
	
	@Override
	public List<Grupo> obtenerGruposTorneo(Torneo torneo) {
		try {
			Query query = em.createNamedQuery("obtenerGruposDeTorneo");
			query.setParameter("idTorneo", torneo.getId());

			List<Grupo> grupos = (List<Grupo>) query.getResultList();
			return grupos;
		} catch (NoResultException ne) {
			logger.fine("No hay grupos: torneo: " + torneo.getId());
		} catch (Exception e) {
			logger.fine("Error al obtener grupos: torneo: " + torneo.getId());
		}
		return null;
	}

}
