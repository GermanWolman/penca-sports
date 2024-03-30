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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table( name="GrupoPenca")
@NamedQueries({
	@NamedQuery(name="obtenerGrupoPenca", query="SELECT gp FROM GrupoPenca gp order by gp.nombre"),
	@NamedQuery(name="obtenerGrupoPencaTorneo", query="SELECT gp FROM GrupoPenca gp where gp.torneo.id=:idTorneo order by gp.nombre"),
	@NamedQuery(name="obtenerUsuariosGrupoPenca", query="SELECT u FROM GrupoPenca gp join gp.usuarios u WHERE gp.id=:idGrupoPenca order by gp.nombre"),
	@NamedQuery(name="obtenerGruposPencasDeUsuarioTorneo", query="SELECT gp FROM GrupoPenca gp join gp.usuarios u WHERE u.id=:idUsuario and gp.torneo.id=:idTorneo order by gp.nombre"),
	@NamedQuery(name="obtenerGrupoPencasUsuarioAdminitrador", query="SELECT gp FROM GrupoPenca gp WHERE gp.usuarioAdministrador.id=:idUsuario order by gp.nombre")
}) 
public class GrupoPenca implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(unique=true)
	private String nombre;
	
	@OneToOne
	private Usuario usuarioAdministrador;
	
	private boolean esAbierta;
	private boolean mandaNotificaciones;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Usuario> usuarios;
	
	@OneToMany(mappedBy="grupoPenca", fetch=FetchType.LAZY)
	private List<Pronostico> pronosticos;
	
	@ManyToOne(targetEntity=Torneo.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Torneo torneo;
	
//	@OneToMany(mappedBy="grupoPenca")
//	private List<Pronostico> pronosticos;

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

	public Usuario getUsuarioAdministrador() {
		return usuarioAdministrador;
	}

	public void setUsuarioAdministrador(Usuario usuarioAdministrador) {
		this.usuarioAdministrador = usuarioAdministrador;
	}

	public boolean isEsAbierta() {
		return esAbierta;
	}

	public void setEsAbierta(boolean esAbierta) {
		this.esAbierta = esAbierta;
	}

	public boolean isMandaNotificaciones() {
		return mandaNotificaciones;
	}

	public void setMandaNotificaciones(boolean mandaNotificaciones) {
		this.mandaNotificaciones = mandaNotificaciones;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pronostico> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}
	
	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	@Override
	public String toString() {
		return "GrupoPenca [id=" + id + ", nombre=" + nombre + ", usuarioAdministrador=" + usuarioAdministrador + ", esAbierta=" + esAbierta + ", mandaNotificaciones=" + mandaNotificaciones + ", usuarios=" + usuarios + ", pronosticos=" + pronosticos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (esAbierta ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (mandaNotificaciones ? 1231 : 1237);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((pronosticos == null) ? 0 : pronosticos.hashCode());
		result = prime * result + ((usuarioAdministrador == null) ? 0 : usuarioAdministrador.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoPenca other = (GrupoPenca) obj;
		if (esAbierta != other.esAbierta)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mandaNotificaciones != other.mandaNotificaciones)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		/*if (pronosticos == null) {
			if (other.pronosticos != null)
				return false;
		} else if (!pronosticos.equals(other.pronosticos))
			return false;
		if (usuarioAdministrador == null) {
			if (other.usuarioAdministrador != null)
				return false;
		} else if (!usuarioAdministrador.equals(other.usuarioAdministrador))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;*/
		return true;
	}
	
	

	
}
