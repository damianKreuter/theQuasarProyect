package com.damk.quasar.model.colections;

import java.util.ArrayList;

import com.damk.quasar.model.InfoSatelite;

/**
 * Clase usada para deserializar el json enviado por HTTP
 * @author damiank
 *
 */
public class InfoObtenida {
	private ArrayList<InfoSatelite> satellites;

	@Override
	public String toString() {
		return "Satelites [satelites=" + satellites + "]";
	}

	public ArrayList<InfoSatelite> getSatelites() {
		return satellites;
	}

	public void setSatelites(ArrayList<InfoSatelite> satelites) {
		this.satellites = satelites;
	}

	public InfoObtenida(ArrayList<InfoSatelite> satelites) {
		super();
		this.satellites = satelites;
	}
	
	
	
	
}
