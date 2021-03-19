package com.damk.quasar.algoritmos;

import com.damk.quasar.model.Posicion;

/**
 * Junta la información de la posición del satélite y su distancia al punto
 * @author damiank
 *
 */
public class DistanciaYPosicion {
	private Posicion posicionSatelite;
	private Float distancia;
	public Posicion getPosicionSatelite() {
		return posicionSatelite;
	}
	public Float getDistancia() {
		return distancia;
	}
	
	public Float getX() {
		return posicionSatelite.getx();
	}
	public Float getY() {
		return posicionSatelite.gety();
	}
	
	public DistanciaYPosicion(Posicion posicionSatelite, Float distancia) {
		super();
		this.posicionSatelite = posicionSatelite;
		this.distancia = distancia;
	}
	
	}
