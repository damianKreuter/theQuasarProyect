package com.damk.quasar.services;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.damk.quasar.algoritmos.EcuacionCircuferencial;
import com.damk.quasar.algoritmos.DistanciaYPosicion;
import com.damk.quasar.model.InfoSatelite;
import com.damk.quasar.model.Satelite;
import com.damk.quasar.model.colections.InfoObtenida;

@Component
public class AlgoritmoPosicionamiento {

	
	public void obtenerPosicionNave(InfoObtenida info, ArrayList<Satelite> satelites)
	{	
		ArrayList<InfoSatelite> infoSatelite = new ArrayList<InfoSatelite>();
		ArrayList<DistanciaYPosicion> ecuaciones = new ArrayList<DistanciaYPosicion>();
		
		for(InfoSatelite infoDe1Satelite: infoSatelite) {
			//Va a matchear si o si porque la validación de nombres ya se hizo un paso previo
			for(Satelite unSatelite: satelites) {
				if(unSatelite.getNombre().equals(infoDe1Satelite.getNombre())){
					ecuaciones.add(new DistanciaYPosicion(unSatelite.getLocation(), infoDe1Satelite.getDistancia()));
				}
			}
		}
		for(DistanciaYPosicion ecuacion: ecuaciones) {
			
		}
		
		
	}
	
}
