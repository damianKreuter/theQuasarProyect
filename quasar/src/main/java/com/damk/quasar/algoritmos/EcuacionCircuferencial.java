package com.damk.quasar.algoritmos;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Ecuación de grado 2 tanto para la variable X como para Y
 * Para construirla se le debe de pasar la posición de X y de Y a partir de eso
 * se calcula el cuadrado de estas y se obtiene el resultado de la ecuación
 * @author damiank
 *
 */
public class EcuacionCircuferencial extends EcuacionLineal {

	private Float grado2X;
	private Float grado2Y;
	
	public EcuacionCircuferencial(Float posicionX, Float posicionY, Float distancia) {
		super();

		grado2X = 1f;
		grado2Y = 1f;
		this.setIndependiente(this.obtenerOrdenadaIndependiente(posicionX, posicionY, distancia));
		this.setxGrado1(-2*posicionX);
		this.setyGrado1(-2*posicionY);
	}
	
	public EcuacionCircuferencial() {}
	
	public Float obtenerOrdenadaIndependiente(Float posicionX, Float posicionY, Float distancia) {
		double x= Math.pow(posicionX, 2);
		double y= Math.pow(posicionY, 2);
		double distancia2 = Math.pow(distancia, 2);
		posicionX = (float) x;
		posicionY = (float) y;
		return (float) (distancia2-x-y);
	}
	
	public EcuacionCircuferencial obtenerRestaDeEcuaciones(EcuacionCircuferencial loQueResta) {
		EcuacionCircuferencial resultado = new EcuacionCircuferencial();
		resultado.setIndependiente(this.getIndependiente()-loQueResta.getIndependiente());
		resultado.setxGrado1(this.getxGrado1()-loQueResta.getxGrado1());
		resultado.setyGrado1(this.getyGrado1()-loQueResta.getyGrado1());
		resultado.setGrado2X(this.getGrado2X()-loQueResta.getGrado2X());
		resultado.setGrado2Y(this.getGrado2Y()-loQueResta.getGrado2Y());
		
		return resultado;
	}
	
	
	public EcuacionCircuferencial obtenerFuncionDependienteDeVariableY() {
		//tomo por supuesto que hubo una resta antes y por lo tanto no hay grado 2
		EcuacionCircuferencial despejeDeY = new EcuacionCircuferencial();
		Float divisor = this.getyGrado1();
		despejeDeY.setIndependiente(this.independiente/divisor);
		despejeDeY.setGrado2Y(0f);
		despejeDeY.setyGrado1(1f);
		
		despejeDeY.setGrado2X(this.getGrado2X()/divisor);
		despejeDeY.setxGrado1(this.getxGrado1()/divisor);
		return despejeDeY;
	}
	
	public Float getIndependiente() {
		return independiente;
	}

	public void setIndependiente(Float independiente) {
		this.independiente = independiente;
	}
	
	public Boolean coordenadasXYPertenecenAEcuacion(Float x, Float y) {
		Float xgrado1 = this.getxGrado1();
		Float ygrado1 = this.getyGrado1();
		Float x2 = this.getGrado2X();
		Float y2 = this.getGrado2Y();
		Float resultadoX = (float) (Math.pow(x2*x,2)+xgrado1*x);
		Float resultadoY = (float) (Math.pow(y2*y,2)+ygrado1*y);
		Float valorFinal = resultadoX+resultadoY;
		return Float.compare(valorFinal, this.getIndependiente())==0;
	}

	public Float getGrado2X() {
		return grado2X;
	}

	public void setGrado2X(Float grado2x) {
		grado2X = grado2x;
	}

	public Float getGrado2Y() {
		return grado2Y;
	}

	public void setGrado2Y(Float grado2y) {
		grado2Y = grado2y;
	}
	
	
	
	
		
}
