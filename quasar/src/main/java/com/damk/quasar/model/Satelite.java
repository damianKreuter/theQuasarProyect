package com.damk.quasar.model;

import java.util.ArrayList;

/*
 * Representa a un satelite el cual se conoce su posición 
 */
public class Satelite {
	private String nombre;
	private Posicion ubicacion;
	
	public Satelite(String nombre, Posicion posicion) {
		super();
		this.nombre = nombre;
		this.ubicacion = posicion;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	public void SetNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}
	
	public Posicion getLocation() {
		return ubicacion;
	}
	public void setLocation(Posicion newLocation) {
		this.ubicacion = newLocation;
	}
	
	/*
	 * Obtiene la distancia en coordenadas X e Y del emisor y el receptor
	 */
	public Posicion getLocation(Posicion originLocation) {
		return ubicacion.obtenerDiferenciaDePosiciones(originLocation);
	}
	
	public Boolean mismoNombre(String nombre) {
		return this.nombre.equals(nombre);
	}
	
}
