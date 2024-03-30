package com.pronostico.penca.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table( name="Torneo")
@NamedQueries({
	@NamedQuery(name="obtenerTorneos", query="SELECT t FROM Torneo t order by t.nombre"),
	@NamedQuery(name="obtenerGruposDeTorneo", query="SELECT g FROM Torneo t join t.grupos g WHERE t.id=:idTorneo"),
	@NamedQuery(name="obtenerEquiposDeTorneo", query="SELECT e FROM Torneo t join t.equipos e WHERE t.id=:idTorneo")
})
public class Torneo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String nombre;
	private String descripcion;
	private String logo;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Grupo> grupos;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Eliminatoria eliminatoria;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Equipo> equipos;
	
	@OneToMany(mappedBy="torneo")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<GrupoPenca> grupoPencas;
	

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public Eliminatoria getEliminatoria() {
		return eliminatoria;
	}

	public void setEliminatoria(Eliminatoria eliminatoria) {
		this.eliminatoria = eliminatoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<GrupoPenca> getGrupoPencas() {
		return grupoPencas;
	}

	public void setGrupoPencas(List<GrupoPenca> grupoPencas) {
		this.grupoPencas = grupoPencas;
	}

	@Override
	public String toString() {
		return "Torneo [id=" + id + ", nombre=" + nombre + ", logo=" + logo + ", grupos=" + grupos + ", eliminatoria=" + eliminatoria + ", equipos=" + equipos + "]";
	}

	
}
