package com.pronostico.penca.ejb.api;

import javax.ejb.Local;

import com.pronostico.penca.model.Pronostico;

@Local
public interface IPronosticoEJBLocal {

	Pronostico ingresarPronostico(Pronostico p);

	Pronostico obtenerPronosticoPorId(Long id);

	void calcularPuntaje(Pronostico p);

	Pronostico pronosticarViaHash(Long hash);

}