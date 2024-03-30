package com.pronostico.penca.ejb.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.pronostico.penca.ejb.api.IUsuariosEjbLocal;
import com.pronostico.penca.ejb.api.IUsuariosEjbRemote;
import com.pronostico.penca.model.Usuario;

@Stateless
public class UsuariosEjb implements IUsuariosEjbLocal, IUsuariosEjbRemote {

	Logger logger = Logger.getLogger("UsuariosEjb");

	@PersistenceContext(unitName = "primary1")
	EntityManager em;

	/******************** Usuario *********************/
	@Override
	public Usuario guardarUsuario(Usuario u) {
		return em.merge(u);
	}

	@Override
	public Usuario obtenerUsuario(String usuario, String password) throws Exception {
		Query query = em.createNamedQuery("obtenerUsuarioPorUsuarioYPassword");
		query.setParameter("usuario", usuario).setParameter("pass", password);

		List<Usuario> usuarios = (List<Usuario>) query.getResultList();
		if (usuarios.size() > 0) {
			return usuarios.get(0);
		}
		return null;

	}

	@Override
	public Usuario obtenerUsuarioPorUsuario(String usuario) throws Exception {
		Query query = em.createNamedQuery("obtenerUsuarioPorUsuario");
		query.setParameter("usuario", usuario);

		List<Usuario> usuarios = (List<Usuario>) query.getResultList();
		if (usuarios.size() > 0) {
			return usuarios.get(0);
		}
		return null;

	}

	@Override
	public Usuario obtenerUsuarioPorId(Long idUsuario) throws Exception {
		return em.find(Usuario.class, idUsuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {

		Query query = em.createNamedQuery("obtenerUsuarios");

		List<Usuario> usuarios = (List<Usuario>) query.getResultList();

		return usuarios;

	}

}
