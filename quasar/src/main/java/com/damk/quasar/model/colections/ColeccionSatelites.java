package com.damk.quasar.model.colections;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.damk.quasar.model.InfoSatelite;
import com.damk.quasar.model.Posicion;
import com.damk.quasar.model.Satelite;

@Component
public class ColeccionSatelites {
	
	private ArrayList<Satelite> listaSatelites;
	
	public ArrayList<Satelite> getListaSatelites() {
		return listaSatelites;
	}

	public void setListaSatelites(ArrayList<Satelite> listaSatelites) {
		this.listaSatelites = listaSatelites;
	}

	@PostConstruct
    public void init() {
		listaSatelites = new ArrayList<Satelite>();
		
		listaSatelites.add(this.creacionSatelite("kenobi", new Posicion(14, 45)));
		listaSatelites.add(this.creacionSatelite("skywalker", new Posicion(80, 70)));
		listaSatelites.add(this.creacionSatelite("sato", new Posicion(71, 50)));
		/*
		listaSatelites.add(this.creacionSatelite("kenobi", new Posicion(-500f, 200f)));
		listaSatelites.add(this.creacionSatelite("skywalker", new Posicion(100f, 100f)));
		listaSatelites.add(this.creacionSatelite("sato", new Posicion(500f, 100f)));
		*/
    } 
	
	private Satelite creacionSatelite(String nombre, Posicion ubicacion) {
		return new Satelite(nombre, ubicacion);
	}

	
}
