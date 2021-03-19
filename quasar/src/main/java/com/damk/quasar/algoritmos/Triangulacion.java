package com.damk.quasar.algoritmos;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.damk.quasar.excepciones.ExcepcionSatelite;
import com.damk.quasar.model.Posicion;
/**
 * Clase para la calculación del punto que comparten 3 ecuaciones mediante triangulación
 * En caso de que dihco punto no exista entonces se lanzará una excepción diciendo que no
 * es posible hallarla
 * @author damiank
 *
 */

@Component
public class Triangulacion {

	
	public Posicion obtenerPosicionDeNave(ArrayList<DistanciaYPosicion> datosSatelite) throws ExcepcionSatelite {

		ArrayList<EcuacionCircuferencial> ecuaciones = new ArrayList<EcuacionCircuferencial>();
		
		for(DistanciaYPosicion datosIniciales:datosSatelite) {
			ecuaciones.add(new EcuacionCircuferencial(datosIniciales.getX(), datosIniciales.getY(), datosIniciales.getDistancia()));
		}
		Posicion coordenadasObtenidas = this.obtenerTriangulacion(ecuaciones);
		return coordenadasObtenidas;
	}
	
	private Posicion obtenerTriangulacion(ArrayList<EcuacionCircuferencial> ecuaciones) {
		if(ecuaciones.size()==3) {
			EcuacionCircuferencial datosEcuacion1 = ecuaciones.get(0);
			EcuacionCircuferencial datosEcuacion2 = ecuaciones.get(1);
			EcuacionCircuferencial datosEcuacion3 = ecuaciones.get(2);
			
			EcuacionCircuferencial diferenciaEcuacion1Y2 = datosEcuacion1.obtenerRestaDeEcuaciones(datosEcuacion2);
			EcuacionCircuferencial diferenciaEcuacion2Y3 = datosEcuacion2.obtenerRestaDeEcuaciones(datosEcuacion3);
			
			EcuacionCircuferencial despejeDeXde1Y2 = diferenciaEcuacion2Y3.obtenerFuncionDependienteDeVariableY();
			ArrayList<Float> valoresDelDespeje = new ArrayList<Float>();
			//Multiplico por -1 ya que es el despeje y cambia su signo
			valoresDelDespeje.add(despejeDeXde1Y2.getxGrado1()*-1);
			valoresDelDespeje.add(despejeDeXde1Y2.getIndependiente());
			Float xObtenido = diferenciaEcuacion1Y2.reemplazarY(valoresDelDespeje);
			Float yObtenido = despejeDeXde1Y2.reemplazarValorObtenidoDeX(xObtenido);
			if(verificarCoordenadasEnEcuaciones(xObtenido, yObtenido, ecuaciones)) {
				return new Posicion(xObtenido,yObtenido);
			}
			throw new ExcepcionSatelite("No existen coordenadas al cual pertenescan las distancias a dichos satelites");
		} else {
			throw new ExcepcionSatelite("Hay mas de 3 ecuaciones para realizar la triangulación, deben ser 3 si o si");
		}
	}
	
	private Boolean verificarCoordenadasEnEcuaciones(Float xObtenido, Float yObtenido, ArrayList<EcuacionCircuferencial> ecuaciones) {
		return ecuaciones.stream().allMatch(ecuacion->ecuacion.coordenadasXYPertenecenAEcuacion(xObtenido, yObtenido));
	}
	
}
