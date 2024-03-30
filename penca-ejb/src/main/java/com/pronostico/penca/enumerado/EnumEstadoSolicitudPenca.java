package com.pronostico.penca.enumerado;

public enum EnumEstadoSolicitudPenca {
	
	PENDIENTE("P","Pendiente"),
	APROBADO("A", "Aprobado");
	
	private final String value;
	private final String name;
	
	EnumEstadoSolicitudPenca(String value, String name){
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public static EnumEstadoSolicitudPenca getEnumFromValue(String value){
		for(EnumEstadoSolicitudPenca e : values()){
			if(e.toString().equals(value)){
				return e;
			}
		}
		return null;
	}
	
	public static String getEnumName(String name){
		for(EnumEstadoSolicitudPenca e : values()){
			if(e.toString().equals(name)){
				return e.getName();
			}
		}
		return null;
	}
}
