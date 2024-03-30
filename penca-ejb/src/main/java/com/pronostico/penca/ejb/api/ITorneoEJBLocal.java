package com.pronostico.penca.ejb.api;

import java.util.List;

import javax.ejb.Local;

import com.pronostico.penca.model.Equipo;
import com.pronostico.penca.model.Fecha;
import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Partido;
import com.pronostico.penca.model.Pronostico;
import com.pronostico.penca.model.Torneo;
import com.pronostico.penca.model.Usuario;

@Local
public interface ITorneoEJBLocal {

	public Torneo altaTorneo(Torneo t);
	public Torneo obtenerTorneoPorId(Long id);
	public List<Torneo> obtenerTorneosVigentes();
	public List<Equipo> guardarEquipos(List<Equipo> equipos);
	Fecha obtenerFechaPorId(Long id);
//	void guardarPronosticoPartido(Partido p, Usuario u);
	List<Pronostico> obtenerPronosticosUsuario(Usuario u, GrupoPenca g);
	GrupoPenca obtenerGrupoPencaPorId(Long id);
	Pronostico obtenerPronosticoUsuarioPartido(Usuario u, GrupoPenca g, Partido p);
	void guardarPronostico(Pronostico p);
	Usuario obtenerUsuarioPorId(Long id);
	void guardarPronosticoPartido(Partido p, Usuario u, GrupoPenca gp);
	List<Grupo> obtenerGruposTorneo(Torneo torneo);

}
