package com.damk.quasar.model;

import java.util.ArrayList;

public class InfoSatelite {

	private String name;
	private Float distance;
	private ArrayList<String> message;
	public InfoSatelite() {
		super();
	}
	public String getNombre() {
		return name;
	}
	public void setNombre(String nombre) {
		this.name = nombre;
	}
	public Float getDistancia() {
		return distance;
	}
	public void setDistancia(Float distancia) {
		this.distance = distancia;
	}
	public ArrayList<String> getMensajeEncriptado() {
		return message;
	}
	public void setMensajeEncriptado(ArrayList<String> mensajeEncriptado) {
		this.message = mensajeEncriptado;
	}
	@Override
	public String toString() {
		return "InfoSatelite [name=" + name + ", distance=" + distance + ", mesasage=" + message + "]";
	}
	
	public Integer cantidadPalabrasObtenidasEnMensaje() {
		return this.message.size();
	}
	
	 
	
}
