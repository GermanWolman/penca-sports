package com.pronostico.penca.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table( name="Usuario")
@NamedQueries({
	@NamedQuery(name="obtenerUsuarios", query="SELECT u FROM Usuario u order by u.usuario"),
	@NamedQuery(name="obtenerUsuarioPorUsuarioYPassword", query="SELECT u FROM Usuario u WHERE u.usuario=:usuario and u.password=:pass"),
	@NamedQuery(name="obtenerUsuarioPorUsuario", query="SELECT u FROM Usuario u WHERE u.usuario=:usuario"),
	@NamedQuery(name="obtenerGruposPencasDeUsuario", query="SELECT gp FROM Usuario u join u.grupoPencas gp WHERE u.id=:idUsuario"),
	@NamedQuery(name="obtenerPronosticosDeUsuario", query="SELECT p FROM Usuario u join u.pronosticos p WHERE u.id=:idUsuario"),
	@NamedQuery(name="obtenerSolicitudesPencaDeUsuario", query="SELECT sp FROM Usuario u join u.solicitudesPencas sp WHERE u.id=:idUsuario")
})
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
//	@Id
	private String nombre;
	private String apellido;
	
	@Column(unique=true)
	private String usuario;
	private String password;
	
	private String imagen;
	private String email;
	
	private String celular;
	
//	@OneToMany(fetch=FetchType.EAGER)
//	private List<Jugador> jugadores;
	
//	@OneToMany
//	private List<Comentario> comentarios;
	
	@ManyToMany(mappedBy="usuarios", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<GrupoPenca> grupoPencas;
	
	@OneToMany(mappedBy="usuario")
	private List<Pronostico> pronosticos;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SolicitudPenca> solicitudesPencas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public List<SolicitudPenca> getSolicitudesPencas() {
		return solicitudesPencas;
	}

	public void setSolicitudesPencas(List<SolicitudPenca> solicitudesPencas) {
		this.solicitudesPencas = solicitudesPencas;
	}

	public List<GrupoPenca> getGrupoPencas() {
		return grupoPencas;
	}

	public void setGrupoPencas(List<GrupoPenca> grupoPencas) {
		this.grupoPencas = grupoPencas;
	}

	public List<Pronostico> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", usuario=" + usuario
				+ ", password=" + password + ", imagen=" + imagen + ", email=" + email + ", celular=" + celular
				//+ ", pronosticos=" + pronosticos 
				+ "]";
	}
	
}
