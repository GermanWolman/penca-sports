package com.pronostico.penca.DT;

import java.io.Serializable;

import com.pronostico.penca.enumerado.EnumEstadoGrupoPencauUsuario;
import com.pronostico.penca.model.GrupoPenca;
import com.pronostico.penca.model.Torneo;
import com.pronostico.penca.model.Usuario;

public class DTGrupoPenca implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GrupoPenca grupoPenca;
	private EnumEstadoGrupoPencauUsuario estado;
	
	public DTGrupoPenca() {
	
	}

	public GrupoPenca getGrupoPenca() {
		return grupoPenca;
	}

	public void setGrupoPenca(GrupoPenca grupoPenca) {
		this.grupoPenca = grupoPenca;
	}

	public EnumEstadoGrupoPencauUsuario getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoGrupoPencauUsuario estado) {
		this.estado = estado;
	}

	
}
