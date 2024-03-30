package com.pronostico.penca.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name="Pronostico")
@NamedQueries({
	@NamedQuery(name="obtenerPronosticos", query="SELECT p FROM Pronostico p order by p.id"),
	@NamedQuery(name="obtenerPronosticoUsuarioPartidoGrupoPenca", query="SELECT p FROM Pronostico p WHERE p.partido.id=:partido and p.grupoPenca.id=:gp and p.usuario.id=:usuario"),
	@NamedQuery(name="obtenerPronosticosUsuarioGrupoPenca", query="SELECT p FROM Pronostico p WHERE p.grupoPenca.id=:gp and p.usuario.id=:usuario")
})
public class Pronostico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Equipo equipoGanador;
	
	private Integer golesLocal;
	private Integer golesVisitante;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pronostico_partido_id")
	private Partido partido;
	
	private Integer puntaje;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private GrupoPenca grupoPenca;
	
	@ManyToOne(targetEntity=Usuario.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Equipo getEquipoGanador() {
		return equipoGanador;
	}

	public void setEquipoGanador(Equipo equipoGanador) {
		this.equipoGanador = equipoGanador;
	}

	public Integer getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(Integer golesLocal) {
		this.golesLocal = golesLocal;
	}

	public Integer getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(Integer golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public Integer getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Integer puntaje) {
		this.puntaje = puntaje;
	}

	public GrupoPenca getGrupoPenca() {
		return grupoPenca;
	}

	public void setGrupoPenca(GrupoPenca grupoPenca) {
		this.grupoPenca = grupoPenca;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Pronostico [id=" + id + ", equipoGanador=" + equipoGanador + ", golesLocal=" + golesLocal + ", golesVisitante=" + golesVisitante + ", partido=" + partido + ", puntaje=" + puntaje + "]";
	}
	
	

}
