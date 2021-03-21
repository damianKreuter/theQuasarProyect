package com.damk.quasar.model.colections;

import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.damk.quasar.excepciones.ExcepcionSatelite;
import com.damk.quasar.model.InfoSatelite;
import com.damk.quasar.model.Posicion;
import com.damk.quasar.model.Satelite;
import com.damk.quasar.model.Transmision;

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
	
	public void cambiarCoordenasDeSatelite(String nombreSatelite, Posicion nuevaPosicion) throws ExcepcionSatelite {
		if(this.existeSateliteConNombre(nombreSatelite)){
			listaSatelites.stream()
			.filter(satelite->satelite.getNombre().equals(nombreSatelite))
			.forEach(satelite->satelite.setLocation(nuevaPosicion));
		} else {
			throw new ExcepcionSatelite("No existe satelite con nombre "+nombreSatelite);
		}
	}
	
	public Boolean existeSateliteConNombre(String nombreBuscado) {
		Optional<Satelite> sateliteBuscado = this.getListaSatelites().stream()
				.filter(satelite -> satelite.getNombre().equals(nombreBuscado))
				.findFirst();
			return sateliteBuscado.isPresent();
	}
	
	private Satelite creacionSatelite(String nombre, Posicion ubicacion) {
		return new Satelite(nombre, ubicacion);
	}

	
}
