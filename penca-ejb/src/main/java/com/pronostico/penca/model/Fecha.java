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
import javax.persistence.Table;

@Entity
@Table( name="Fecha")
@NamedQueries({
	@NamedQuery(name="obtenerFechas", query="SELECT f FROM Fecha f order by f.nombre")
})
public class Fecha implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Partido> partidos;

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

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}

	@Override
	public String toString() {
		return "Fecha [id=" + id + ", nombre=" + nombre + ", partidos=" + partidos + "]";
	}

	
	
}
