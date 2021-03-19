package com.damk.quasar.model;

import com.google.gson.Gson;

public class RespuestaSatelite {
	private Posicion posicion;
	private String mensaje;
	public Posicion getPosicion() {
		return posicion;
	}
	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public RespuestaSatelite(Posicion posicion, String mensaje) {
		super();
		this.posicion = posicion;
		this.mensaje = mensaje;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	
}
