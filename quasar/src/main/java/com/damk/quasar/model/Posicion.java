package com.damk.quasar.model;

import java.lang.Math;
import java.math.BigDecimal;

public class Posicion {
	private float x;
	private float y;
	
	public Posicion(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public float getx() {
		return x;
	}
	public void setx(float x) {
		this.x = x;
	}
	public float gety() {
		return y;
	}
	public void sety(float y) {
		this.y = y;
	}
	
	public Posicion obtenerDiferenciaDePosiciones(Posicion transmitterLocationMessage) {
		float diffPostionX = this.diffBetween(x, transmitterLocationMessage.getx());
		float diffPostionY = this.diffBetween(y, transmitterLocationMessage.gety());
		return new Posicion(diffPostionX, diffPostionY);
	}
	
	/*
	 * Diff Between calcutale the difference distances between the satelite and the transmitter for a current position
	 */
	private  float diffBetween(float satelitePosition, float transmitterPosition) {
		return satelitePosition - transmitterPosition;
	}
	
	private float realDistanceBetweenSateliteAndTransmitter(float x, float y) {
		/*
		 * Se tendría que realizarse con BIGDECIMAL pero la funcionalidad de SQRT es exclusiva de JAVA 9
		 * la cual se está trabajando con JAVA 8 actualmente por lo tanto se utilizaremos Double a cambio
		 * de perder parte de la precisión en los calculos
		 */
		
		double position1 = x;
		double position2 = y;
		double result =  Math.hypot(position1, position2);		
		return (float) result;
	}
	
}
