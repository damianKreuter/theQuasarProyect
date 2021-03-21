package com.damk.quasar.model;

import java.util.ArrayList;

public class DatosTransmisionReturn {
	private String satelite;
	private Float distancia;
	private ArrayList<String> mensaje;

	public DatosTransmisionReturn(String satelite, Float distancia, ArrayList<String> mensaje) {
		super();
		this.satelite = satelite;
		this.distancia = distancia;
		this.mensaje = mensaje;
	}
	
	public String getSatelite() {
		return satelite;
	}
	public void setSatelite(String satelite) {
		this.satelite = satelite;
	}
	public Float getDistancia() {
		return distancia;
	}
	public void setDistancia(Float distancia) {
		this.distancia = distancia;
	}
	public ArrayList<String> getMensaje() {
		return mensaje;
	}
	public void setMensaje(ArrayList<String> mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
