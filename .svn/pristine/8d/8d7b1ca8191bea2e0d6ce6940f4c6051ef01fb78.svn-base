package com.pronostico.penca.ejb.api;

import java.util.List;
import javax.ejb.Remote;

import com.pronostico.penca.model.Usuario;

@Remote
public interface IUsuariosEjbRemote {

	/******************** Usuario *********************/
	Usuario guardarUsuario(Usuario u);

	Usuario obtenerUsuario(String usuario, String password) throws Exception;

	Usuario obtenerUsuarioPorUsuario(String usuario) throws Exception;

	Usuario obtenerUsuarioPorId(Long idUsuario) throws Exception;

	List<Usuario> obtenerUsuarios();

}