package com.pronostico.penca.DT;

import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Torneo;
import com.pronostico.penca.model.Usuario;

public class DTUsuarioPuntaje {
	
	private Usuario usuario;
	private int puuntaje;
	private GrupoPenca grupoPenca;
	private Torneo torneo;
	
	public DTUsuarioPuntaje() {
	
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public int getPuuntaje() {
		return puuntaje;
	}
	public void setPuuntaje(int puuntaje) {
		this.puuntaje = puuntaje;
	}
	public GrupoPenca getGrupoPenca() {
		return grupoPenca;
	}
	public void setGrupoPenca(GrupoPenca grupoPenca) {
		this.grupoPenca = grupoPenca;
	}
	public Torneo getTorneo() {
		return torneo;
	}
	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}
	
	
	
}
