package com.damk.quasar.model;

import java.util.ArrayList;

public class Transmision {

	private Satelite satelite;
	private DatosTranmsision data;
	
	public Transmision(Satelite satelite, DatosTranmsision data) {
		super();
		this.setSatelite(satelite);
		this.setData(data);
	}
	
	public Satelite getSatelite() {
		return satelite;
	}
	public void setSatelite(Satelite satelite) {
		this.satelite = satelite;
	}
	public Float getDistanciaASatelite() {
		return data.getDistanciaASatelite();
	}
	public void setDistanciaASatelite(Float distanciaASatelite) {
		this.data.setDistanciaASatelite(distanciaASatelite);
	}
	public ArrayList<String> getMensajeRecibido() {
		return this.data.getMensajeRecibido();
	}
	public void setMensajeRecibido(ArrayList<String> mensajeRecibido) {
		this.data.setMensajeRecibido(mensajeRecibido);
	}
	public DatosTranmsision getData() {
		return data;
	}
	public void setData(DatosTranmsision data) {
		this.data = data;
	}
	
	
	
	
}
