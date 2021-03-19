package com.damk.quasar.model;

import java.util.ArrayList;

public class DatosTranmsision {
	private Float distance;
	private ArrayList<String> message;
	
	public DatosTranmsision(Float distanciaASatelite, ArrayList<String> mensajeRecibido) {
		super();
		this.distance = distanciaASatelite;
		this.message = mensajeRecibido;
	}
	public Float getDistanciaASatelite() {
		return distance;
	}
	public void setDistanciaASatelite(Float distanciaASatelite) {
		this.distance = distanciaASatelite;
	}
	public ArrayList<String> getMensajeRecibido() {
		return message;
	}
	public void setMensajeRecibido(ArrayList<String> mensajeRecibido) {
		this.message = mensajeRecibido;
	}
	
	
}
