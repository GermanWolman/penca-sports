package com.pronostico.penca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

@Entity
@Table( name="Partido")
@NamedQueries({
	@NamedQuery(name="obtenerPartidos", query="SELECT p FROM Partido p order by p.fecha"),
	@NamedQuery(name="obtenerPartidoPorFecha", query="SELECT p FROM Partido p WHERE p.fecha=:fecha"),
	@NamedQuery(name="obtenerPartidoPorAlias", query="SELECT p FROM Partido p WHERE p.alias=:alias"),
	@NamedQuery(name="obtenerEquipoLocalDeUnPartido", query="SELECT p.equipoLocal FROM Partido p WHERE p.fecha=:fecha"),
	@NamedQuery(name="obtenerEquipoVisitanteDeUnPartido", query="SELECT p.equipoVisitante FROM Partido p WHERE p.fecha=:fecha")
})
public class Partido implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private Date fecha;
	private String alias;
	
	@OneToOne
	private Equipo equipoLocal;
	@OneToOne
	private Equipo equipoVisitante;
	
	private Integer golesReglamentariosLocal = null;
	private Integer golesReglamentariosVisitante = null;
	private Integer golesExtraLocal = null;
	private Integer golesExtraVisitante = null;
	private Integer golesPenalesLocal = null;
	private Integer golesPenalesVisitante = null;
	
	private Integer golesPronosticoLocal = null;
	private Integer golesPronosticoVisitante = null;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="partido")
	private List<Pronostico> pronosticos;
	
//	@OneToMany(fetch=FetchType.EAGER)
//	private List<Comentario> comentarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getGolesReglamentariosLocal() {
		return golesReglamentariosLocal;
	}

	public void setGolesReglamentariosLocal(Integer golesReglamentariosLocal) {
		this.golesReglamentariosLocal = golesReglamentariosLocal;
	}

	public Integer getGolesReglamentariosVisitante() {
		return golesReglamentariosVisitante;
	}

	public void setGolesReglamentariosVisitante(Integer golesReglamentariosVisitante) {
		this.golesReglamentariosVisitante = golesReglamentariosVisitante;
	}

	public Integer getGolesExtraLocal() {
		return golesExtraLocal;
	}

	public void setGolesExtraLocal(Integer golesExtraLocal) {
		this.golesExtraLocal = golesExtraLocal;
	}

	public Integer getGolesExtraVisitante() {
		return golesExtraVisitante;
	}

	public void setGolesExtraVisitante(Integer golesExtraVisitante) {
		this.golesExtraVisitante = golesExtraVisitante;
	}

	public Integer getGolesPenalesLocal() {
		return golesPenalesLocal;
	}

	public void setGolesPenalesLocal(Integer golesPenalesLocal) {
		this.golesPenalesLocal = golesPenalesLocal;
	}

	public Integer getGolesPenalesVisitante() {
		return golesPenalesVisitante;
	}

	public void setGolesPenalesVisitante(Integer golesPenalesVisitante) {
		this.golesPenalesVisitante = golesPenalesVisitante;
	}

	public List<Pronostico> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}

	public Integer getGolesPronosticoLocal() {
		return golesPronosticoLocal;
	}

	public void setGolesPronosticoLocal(Integer golesPronosticoLocal) {
		this.golesPronosticoLocal = golesPronosticoLocal;
	}

	public Integer getGolesPronosticoVisitante() {
		return golesPronosticoVisitante;
	}

	public void setGolesPronosticoVisitante(Integer golesPronosticoVisitante) {
		this.golesPronosticoVisitante = golesPronosticoVisitante;
	}

	/*@Override
	public String toString() {
		return "\nPartido [id=" + id + ", fecha=" + fecha + ", alias=" + alias + ", =>equipoLocal=" + equipoLocal + " VS  equipoVisitante=" + equipoVisitante + "<=, golesReglamentariosLocal=" + golesReglamentariosLocal + ", golesReglamentariosVisitante=" + golesReglamentariosVisitante + ", golesExtraLocal=" + golesExtraLocal + ", golesExtraVisitante=" + golesExtraVisitante + ", golesPenalesLocal=" + golesPenalesLocal + ", golesPenalesVisitante=" + golesPenalesVisitante + ", pronosticos=" + pronosticos + "]";
	}*/
	
	

	
}
