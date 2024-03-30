package com.pronostico.penca.enumerado;

public enum EnumEstadoGrupoPencauUsuario {
	
	PERTENECE("PER","Pertenece"),
	NO_PERTENECE("NPER", "No pertence"),
	PENDIENTE("PEN", "Pendiente");
	
	private final String value;
	private final String name;
	
	EnumEstadoGrupoPencauUsuario(String value, String name){
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public static EnumEstadoGrupoPencauUsuario getEnumFromValue(String value){
		for(EnumEstadoGrupoPencauUsuario e : values()){
			if(e.toString().equals(value)){
				return e;
			}
		}
		return null;
	}
	
	public static String getEnumName(String name){
		for(EnumEstadoGrupoPencauUsuario e : values()){
			if(e.toString().equals(name)){
				return e.getName();
			}
		}
		return null;
	}
}
