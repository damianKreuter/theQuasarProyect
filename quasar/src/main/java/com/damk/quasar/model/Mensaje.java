package com.damk.quasar.model;

import java.util.ArrayList;

public class Mensaje {
	private ArrayList<String> palabras;
	
	public Mensaje(ArrayList<String> palabras) {
		super();
		this.palabras = palabras;
	}

	public ArrayList<String> getPalabras() {
		return palabras;
	}

	public void setPalabras(ArrayList<String> palabras) {
		this.palabras = palabras;
	}
	
	public Integer cantidadPalabras() {
		return this.palabras.size();
	}
	
}
