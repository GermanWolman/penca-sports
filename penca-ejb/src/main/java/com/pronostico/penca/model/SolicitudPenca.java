package com.pronostico.penca.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.pronostico.penca.enumerado.EnumEstadoSolicitudPenca;

@Entity
@Table( name="SolicitudPenca")
@NamedQueries({
	@NamedQuery(name="obtenerSolicitudesPencaDeGrupoPenca", query="SELECT sp FROM SolicitudPenca sp join sp.grupoPenca gp where gp.id=:idGrupoPenca"),
	@NamedQuery(name="obtenerSolicitudesPencaDeUsuarioAdministrador", query="SELECT sp FROM SolicitudPenca sp WHERE sp.grupoPenca.usuarioAdministrador.id=:idUsuario and sp.estado=:estado"),
	@NamedQuery(name="obtenerSolicitudesPenca", query="SELECT sp FROM SolicitudPenca sp ")
})
public class SolicitudPenca implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_grupo_penca")
	private GrupoPenca grupoPenca;
	
	@Column(name="estado")
	@Enumerated(EnumType.STRING)
	private EnumEstadoSolicitudPenca estado;
	
	@ManyToOne(targetEntity=Usuario.class, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrupoPenca getGrupoPenca() {
		return grupoPenca;
	}

	public void setGrupoPenca(GrupoPenca grupoPenca) {
		this.grupoPenca = grupoPenca;
	}

	public EnumEstadoSolicitudPenca getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoSolicitudPenca estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
