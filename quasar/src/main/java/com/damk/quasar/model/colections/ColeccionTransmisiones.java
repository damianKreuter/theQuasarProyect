package com.damk.quasar.model.colections;

import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.damk.quasar.model.DatosTransmisionReturn;
import com.damk.quasar.model.Posicion;
import com.damk.quasar.model.Satelite;
import com.damk.quasar.model.Transmision;

@Component
public class ColeccionTransmisiones {
	
	private ArrayList<Transmision> listaTransmisiones;

	@PostConstruct
    public void init() {
		listaTransmisiones = new ArrayList<Transmision>();
    }

	public ArrayList<Transmision> getListaTransmisiones() {
		return listaTransmisiones;
	}

	public void setListaTransmisiones(ArrayList<Transmision> listaTransmisiones) {
		this.listaTransmisiones = listaTransmisiones;
	} 

	public void agregarTransmision(Transmision transmisionNueva) {
		listaTransmisiones.add(transmisionNueva);
	}
	public void modificarTransmision(Transmision transmisionModificada) {
		String nombreDeTransmisionAModificar = transmisionModificada.getSatelite().getNombre();
		Optional<Transmision> elementoObtenido = listaTransmisiones.stream().filter(elemento->this.esSateliteBuscado(elemento.getSatelite(), nombreDeTransmisionAModificar))
			.findFirst();
		
		if(elementoObtenido.isPresent()) {
			listaTransmisiones.parallelStream().filter(elemento->this.esSateliteBuscado(elemento.getSatelite(), nombreDeTransmisionAModificar))
				.forEach(elemento->{
					elemento.setDistanciaASatelite(transmisionModificada.getDistanciaASatelite());
					elemento.setMensajeRecibido(transmisionModificada.getMensajeRecibido());
				});
		} else {
			this.agregarTransmision(transmisionModificada);
		}
	}
	
	public ArrayList<DatosTransmisionReturn> devolverDatos(){
		ArrayList<DatosTransmisionReturn> datosList = new ArrayList<DatosTransmisionReturn>();
		for(Transmision t : this.getListaTransmisiones()) {
			DatosTransmisionReturn data = new DatosTransmisionReturn(t.getSatelite().getNombre(), t.getDistanciaASatelite(), t.getMensajeRecibido());
			datosList.add(data);
		}
		return datosList;
	}
	
	private Boolean esSateliteBuscado(Satelite satelite, String nombreBuscado) {
		return satelite.getNombre().equals(nombreBuscado);
	}
}
