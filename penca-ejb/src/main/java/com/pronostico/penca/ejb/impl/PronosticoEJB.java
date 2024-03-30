package com.pronostico.penca.ejb.impl;

import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pronostico.penca.ejb.api.IPronosticoEJBLocal;
import com.pronostico.penca.model.Pronostico;

@Stateless
@LocalBean
public class PronosticoEJB implements IPronosticoEJBLocal {

	Logger logger = Logger.getLogger("GrupoEJB");

	@PersistenceContext(unitName = "primary1")
	EntityManager em;

	public Pronostico ingresarPronostico(Pronostico p) {
		p = em.merge(p);

		return p;
	}

	public Pronostico obtenerPronosticoPorId(Long id) {
		Pronostico p = em.find(Pronostico.class, id);
		return p;
	}

	public void calcularPuntaje(Pronostico p) {
		// Forma de calcular el puntaje
		p.setPuntaje(0);
		p = em.merge(p);
	}

	public Pronostico pronosticarViaHash(Long hash) {
		// De alguna manera valido el hash y obtengo el id del pronostico.
		return null;
	}

}
