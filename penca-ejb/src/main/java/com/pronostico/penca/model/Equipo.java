package com.pronostico.penca.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name="Equipo")
@NamedQueries({
	@NamedQuery(name="obtenerEquipos", query="SELECT e FROM Equipo e order by e.nombre"),
	@NamedQuery(name="obtenerEquipoPorNombre", query="SELECT e FROM Equipo e WHERE e.nombre=:nombre")
})
public class Equipo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String nombre;
	private String logo;
	private String url;
	private String informacion;
	

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	@Override
	public String toString() {
		return "\nEquipo [id=" + id + ", nombre=" + nombre + ", logo=" + logo + ", url=" + url + ", informacion=" + informacion + "]";
	}

	
}