package com.damk.quasar.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damk.quasar.algoritmos.EcuacionCircuferencial;
import com.damk.quasar.algoritmos.DistanciaYPosicion;
import com.damk.quasar.algoritmos.Triangulacion;
import com.damk.quasar.excepciones.ExcepcionSatelite;
import com.damk.quasar.model.DatosTranmsision;
import com.damk.quasar.model.InfoSatelite;
import com.damk.quasar.model.Mensaje;
import com.damk.quasar.model.Posicion;
import com.damk.quasar.model.RespuestaSatelite;
import com.damk.quasar.model.Satelite;
import com.damk.quasar.model.Transmision;
import com.damk.quasar.model.colections.ColeccionSatelites;
import com.damk.quasar.model.colections.ColeccionTransmisiones;
import com.damk.quasar.model.colections.InfoObtenida;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servicio para la interacción entre la nave y los satélites
 * Funciones que pueden realizar:
 * 		*Obtener la ubicación de una nave a partir de la información de la distancia
 * 			de la nave hasta los satélites y las coordenasda de las mismas
 * 		*Obtener el mensaje que tiene cada satélite que recibió de la nave
 * 			La misma podria tener interferencia así que  se tendrá que mergear y en el
 * 			caso de que esté incompleto o que las palabras no son iguales entre los satélites
 * 			Entonces lanzaría una excepción.
 * @author damiank
 *
 */
@Service
public class TopSecretService {

	@Autowired
	private AlgoritmoPosicionamiento algoritmoPosicionamiento;
	
	@Autowired
	private Triangulacion algoritmoTriangulacion;
	
	@Autowired
	private ColeccionSatelites colSatelites;
	
	@Autowired
	private ColeccionTransmisiones colTransmisiones;
	/*
	public String darDatos(Satelite satelite) {
		Posicion posicion = satelite.getLocation();
		String mensaje = satelite.obtenerMensaje();
		RespuestaSatelite response = new RespuestaSatelite(posicion, mensaje);
		Gson gson = new Gson();
		String json = gson.toJson(response);
		return json;
	}
	*/
	
	public RespuestaSatelite obtenerUbicacionYDatosAlmacenados() throws ExcepcionSatelite {
		Type type = new TypeToken<DatosTranmsision>(){}.getType();
		
		ArrayList<Mensaje> mensajesAlmacenados = (ArrayList<Mensaje>) colTransmisiones.getListaTransmisiones().stream()
				.map(transmision->new Mensaje(transmision.getMensajeRecibido()))
				.collect(Collectors.toList());
		String mensaje = this.getMessage(mensajesAlmacenados);
		ArrayList<DistanciaYPosicion> datosDePosicionamiento = new ArrayList<DistanciaYPosicion>();
	//	ArrayList<DistanciaYPosicion> datosDePosicionamiento = this.relacionarDistanciaASateliteYSuPosicion(info);
		colTransmisiones.getListaTransmisiones().stream()
			.forEach(elemento->{
				datosDePosicionamiento.add(new DistanciaYPosicion(elemento.getSatelite().getLocation(), elemento.getDistanciaASatelite()));
			});
		Posicion posicionNave = this.getLocation(datosDePosicionamiento);
		return new RespuestaSatelite(posicionNave, mensaje);
	}
	
	/*
	 * Guarda los datos de la posición y el mensaje que se recibió de una nave actualizando dichos datos 
	 * para un satélite del cual se sabe su nombre
	 * En caso de que dicho nombr eno figura entre los satélites existentes entonces lanza una excepción
	 */
	public void guardarDatosDeUbicacionYMensaje(String payload, String nombreSatelite) throws ExcepcionSatelite {
		Type type = new TypeToken<DatosTranmsision>(){}.getType();
		DatosTranmsision infoTransmision =  new Gson().fromJson(payload, type);
		
		Optional<Satelite> sateliteBuscado = colSatelites.getListaSatelites().stream()
			.filter(satelite -> satelite.getNombre().equals(nombreSatelite))
			.findFirst();
		if(sateliteBuscado.isPresent()) {
			Satelite sateliteEncontrado = sateliteBuscado.get();
			Transmision transmisionEntrante = new Transmision(sateliteEncontrado, infoTransmision);
			colTransmisiones.modificarTransmision(transmisionEntrante);
			int i =1;
		} else {
			throw new ExcepcionSatelite("No existe ningun satélite con el nombre de "+nombreSatelite);
		}
	}
	
	public RespuestaSatelite ejecutarServicio(String payload) throws ExcepcionSatelite {
		Type type = new TypeToken<InfoObtenida>(){}.getType();
		InfoObtenida info =  new Gson().fromJson(payload, type);
		//Una vez obtenida la inforamción sobre los satélites queda ordenar
		//Primero ordenaremos el mensaje 
		
		if(!this.todosLosSatelitesExisten(info.getSatelites())) {
			throw new ExcepcionSatelite("Algún satelite de los obtenidos cuyo nombre no corresponde a ninguno de los existentes");
		} 
		ArrayList<Mensaje> mensajesEmitidos = (ArrayList<Mensaje>) info.getSatelites().stream()
				.map(satelite->new Mensaje(satelite.getMensajeEncriptado()))
				.collect(Collectors.toList());
		String mensaje = this.getMessage(mensajesEmitidos);
		ArrayList<DistanciaYPosicion> datosDePosicionamiento = this.relacionarDistanciaASateliteYSuPosicion(info);
		Posicion posicionNave = this.getLocation(datosDePosicionamiento);
		return new RespuestaSatelite(posicionNave, mensaje);
	}
	
	private Boolean todosLosSatelitesExisten(ArrayList<InfoSatelite> satelites) {
		return satelites.stream().allMatch(infoSatelite->this.perteneceAAlgunSateliteConocido(infoSatelite.getNombre()));
	}
	
	/*
	 * De la info obtenida, obtendremos el nombre al satelite que va dirigido y la distancia respectiva
	 * Devuelve una excepción en caso de que no se pueden obtener la posición que se requiere
	 */
	private Posicion getLocation(ArrayList<DistanciaYPosicion> datosDeEcuaciones) throws ExcepcionSatelite {
		return algoritmoTriangulacion.obtenerPosicionDeNave(datosDeEcuaciones);
	}
	
	private ArrayList<DistanciaYPosicion> relacionarDistanciaASateliteYSuPosicion(InfoObtenida infoObtenida) {
		ArrayList<DistanciaYPosicion> datosIniciales = new ArrayList<DistanciaYPosicion>();
		ArrayList<Satelite> satelitesDisponibles = colSatelites.getListaSatelites();
		//Cargo los datos iniciales, DISTANCIA y COORDENADAS DE LOS SATELITES
		for(InfoSatelite infoDe1Satelite : infoObtenida.getSatelites()) {
			Satelite sateliteAlQuePertenece = satelitesDisponibles.stream()
            	.filter(satelite -> satelite.getNombre().equals(infoDe1Satelite.getNombre()))
            	.findFirst()
            	.get();
			
			datosIniciales.add(new DistanciaYPosicion(sateliteAlQuePertenece.getLocation(), infoDe1Satelite.getDistancia()));
		}
		return datosIniciales;
	}
	
	/*
	 * Traduce el mensaje obtenido eliminando las interferencias que se obtuvieron
	 * Para ello verificamos primero si todos todos los satelites recibieron el mismo numero de palabras y/o
	 * interferencias, en caso positivo, pasamos a agregarlo a un Array del cual tendrá el mensaje del
	 * primer satélite y con eso comparamos palabra a palabra con los mensajes del resto de satelites.
	 * En caso de que la primera palabra sea un defase, se reemplazara automaticamente por la palabra
	 * de la segunda, en caso de que la segunda palabra no sea defase y sea la misma que la primera, entonces
	 * retorna la primer palabra. En caso de que no se cumple se tira una excepcion porque se recibió
	 * una palabra que no corresponde al mensaje.
	 */
	private String getMessage(ArrayList<Mensaje> mensajesObtenidos) throws ExcepcionSatelite {
		ArrayList<String> mensajesDepurados  = new ArrayList<String>();
		if(this.losMensajesRecibidosSonTodosDelMismosTamanioDeMensajeRecibido(mensajesObtenidos)) {
			for(Mensaje info: mensajesObtenidos) {
				if(mensajesDepurados.isEmpty()) {
					mensajesDepurados.addAll(info.getPalabras());
				} else {
					ArrayList<String> palabrasDelMensaje = info.getPalabras();
					for(int index=0;index<palabrasDelMensaje.size();index++) {
						mensajesDepurados.set(index, this.ponerPalabra(mensajesDepurados, palabrasDelMensaje, index));
					}
				}
			}
			if(mensajesDepurados.stream().anyMatch(palabra->this.esPalabraDesfasada(palabra))) {
				throw new ExcepcionSatelite("El mensaje no está completo ya que existe información que se perdió por las interferencias");
			}
		} else {
			throw new ExcepcionSatelite("No todos los satelites reicibieron la misma cantidad de palabras del mensaje original (incluyendo las interferencias)");
		}
		return this.armarMensajeObtenido(mensajesDepurados);
	}
	
	private String ponerPalabra(ArrayList<String> primerCol, ArrayList<String> segundoCol, Integer posicionIndex) throws ExcepcionSatelite {
		String palabraPrimerCol = primerCol.get(posicionIndex);
		String palabraSegundoCol = segundoCol.get(posicionIndex);
		if(this.esPalabraDesfasada(palabraPrimerCol)) {
			return palabraSegundoCol;
		}
		if(this.esPalabraDesfasada(palabraSegundoCol)) {
			return palabraPrimerCol;
		}
		if(palabraSegundoCol.contains(palabraPrimerCol)) {
			return palabraPrimerCol;
		}
		
		throw new ExcepcionSatelite("El mensaje original fue alterado durante la recepción y/o no está completo");
	}
	
	private Boolean esPalabraDesfasada(String palabra) {
		return palabra.equals("");
	}
	
	
	private String armarMensajeObtenido(ArrayList<String> mensajesRecibidos) {
		Boolean inicio = true;
		String mensaje = "";
		for(String palabra:mensajesRecibidos) {
			if(inicio) {
				inicio=false;
				mensaje +=palabra;
			} else {
				mensaje = mensaje+" "+palabra;
			}
		}
		return mensaje;
	}
		
	private Boolean losMensajesRecibidosSonTodosDelMismosTamanioDeMensajeRecibido(ArrayList<Mensaje>  mensajesObtenidos) {
		int tamanioMensaje = mensajesObtenidos.get(0).getPalabras().size();
		return mensajesObtenidos.stream().allMatch(unMensaje->unMensaje.cantidadPalabras()==tamanioMensaje);
	}
		
	private Boolean perteneceAAlgunSateliteConocido(String name) {
		return colSatelites.getListaSatelites().stream()
				.anyMatch(satelite->satelite.mismoNombre(name));
	}
	
}
