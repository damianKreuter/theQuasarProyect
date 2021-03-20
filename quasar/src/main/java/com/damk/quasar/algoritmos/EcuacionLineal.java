package com.damk.quasar.algoritmos;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Representación de una ecuación lineal que se deberia representar de la siguiente forma
 * 
 * x+y=independiente 
 * 
 * @author damiank
 *
 */
public class EcuacionLineal {

	protected Float xGrado1;
	protected Float yGrado1;
	protected Float independiente;
	
	public EcuacionLineal(Float valorX, Float valorY, Float independiente) {
		this.xGrado1=valorX;
		this.yGrado1=valorY;
		this.independiente=independiente;
	}
		
	public EcuacionLineal() {
		super();
	}

	//El primer item es X y el segundo el indepedientes
	//Siempre será al grado 1 porque el 2 es 0 por lo tanto quedaria en 0
	public Float reemplazarYEnEcuacionLineal(ArrayList<Float> valoresAReemplazar) {
		Float valorMultiplicadorDeVariable = this.getyGrado1();
		//Con esto multiplico de forma automática así luego los sumo tanto la variable como la independiente
		valoresAReemplazar = (ArrayList<Float>) valoresAReemplazar.stream()
				.map(valor->valor*valorMultiplicadorDeVariable)
				.collect(Collectors.toList());
		
		Float valorX = this.getxGrado1()+valoresAReemplazar.get(0);
		Float independiente = this.getIndependiente()-valoresAReemplazar.get(1);
		
	//	Float algo = this.getGradoDeY().getGrado1()*
		
		return independiente/valorX;
	}
	
	//Tomamos en consideracion que no tiene el segundo
	public Float reemplazarValorObtenidoDeXEnEcuacionLineal(Float xObtenido) {
		Float resultado = this.getIndependiente()-this.getxGrado1()*xObtenido;
		return resultado/this.getyGrado1();
	}

	public Float buscarValorDeYAPartirDeXEnEcuacionLienal(Float x) {
		return this.restarAIndependiente(xGrado1*x)/yGrado1;
	}
	
	public Float buscarValorDeXAPartirDeYEnEcuacionLienal(Float y) {
		return this.restarAIndependiente(yGrado1*y)/xGrado1;
	}
	
	protected Float restarAIndependiente(Float valorARestar) {
		return independiente-valorARestar;
	}

	public Float getxGrado1() {
		return xGrado1;
	}

	public void setxGrado1(Float xGrado1) {
		this.xGrado1 = xGrado1;
	}

	public Float getyGrado1() {
		return yGrado1;
	}

	public void setyGrado1(Float yGrado1) {
		this.yGrado1 = yGrado1;
	}

	public Float getIndependiente() {
		return independiente;
	}

	public void setIndependiente(Float independiente) {
		this.independiente = independiente;
	}
	
	
}
