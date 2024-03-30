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
@Table( name="Grupo")
@NamedQueries({
	@NamedQuery(name="obtenerGrupos", query="SELECT g FROM Grupo g order by g.nombre"),
	@NamedQuery(name="obtenerPartidosDeGrupo", query="SELECT p FROM Grupo g join g.fechas p WHERE g.id=:idGrupo")
})
public class Grupo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private int cantEquipos;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Fecha> fechas;

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

	public List<Fecha> getFechas() {
		return fechas;
	}

	public void setFechas(List<Fecha> fechas) {
		this.fechas = fechas;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nombre=" + nombre + ", cantEquipos=" + cantEquipos + ", fechas=" + fechas + "]";
	}

	
}
