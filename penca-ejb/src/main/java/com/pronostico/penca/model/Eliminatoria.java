package com.pronostico.penca.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name="Eliminatoria")
@NamedQueries({
	@NamedQuery(name="obtenerEliminatoria", query="SELECT e FROM Eliminatoria e order by e.nombre"),
	@NamedQuery(name="obtenerPartidosDeEliminatoria", query="SELECT e FROM Eliminatoria e join e.partidos p WHERE e.id=:idEliminatoria")
})
public class Eliminatoria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String nombre;
	private int cantEquipos;
	
	@OneToMany
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

	public int getCantEquipos() {
		return cantEquipos;
	}

	public void setCantEquipos(int cantEquipos) {
		this.cantEquipos = cantEquipos;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) {
		this.partidos = partidos;
	}

	@Override
	public String toString() {
		return "Eliminatoria [id=" + id + ", nombre=" + nombre + ", cantEquipos=" + cantEquipos + ", partidos=" + partidos + "]";
	}

	
}
