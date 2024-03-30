package com.pronostico.penca.ejb.api;

import java.util.List;

import javax.ejb.Local;

import com.pronostico.penca.model.Grupo;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.SolicitudPenca;

@Local
public interface IGrupoPencaEJBLocal {

	Grupo altaGrupo(Grupo g);

	GrupoPenca obtenerGrupoPencaPorNombre(String nombreGrupo);

	GrupoPenca obtenerGrupoPencaPorId(Long idGrupo);

	List<GrupoPenca> obtenerTodosLosGrupos();

	GrupoPenca obtenerGrupoPencaPorHash(Long hash);

	Long obtenerHashDeGrupoPenca(Long id);

	void enviarMensajeAlGrupo(Long idGrupo);

	List<String> obtenerTablaDelGrupo(Long idGrupoPenca);

	List<String> obtenerTablaGeneral();

	List<GrupoPenca> obtenerGrupoPencasDeUsuario(Long id);
	
	public List<SolicitudPenca> obtenerSolicitudesPencasDeUsuario(Long idUsuario);
	
	public SolicitudPenca guardarSolicitudPenca(SolicitudPenca sp);
	
	public GrupoPenca guardarGrupoPenca(GrupoPenca gp);
	
	public List<GrupoPenca> obtenerGrupoPencasDeUsuarioAdministrador(Long idUsuario);
	
	public List<GrupoPenca> obtenerTodosLosGruposPencasTorneo(Long idTorneo);
	
	public List<GrupoPenca> obtenerGrupoPencasDeUsuarioTorneo(Long idUsuario, Long idTorneo);
	
	public List<SolicitudPenca> obtenerSolicitudesPencasDeUsuarioAdministrador(Long idUsuario);

}
