package com.pronostico.penca.ejb.api;

import java.util.List;

import javax.ejb.Local;

import com.pronostico.penca.model.Usuario;

@Local
public interface IUsuariosEjbLocal {

	public Usuario obtenerUsuario(String usuario, String password)
			throws Exception;

	public Usuario guardarUsuario(Usuario u);

	public Usuario obtenerUsuarioPorUsuario(String usuario) throws Exception;

	public List<Usuario> obtenerUsuarios();

	public Usuario obtenerUsuarioPorId(Long idUsuario) throws Exception;

}
