package com.damk.quasar.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.damk.quasar.excepciones.ExcepcionSatelite;
import com.damk.quasar.services.TopSecretService;
import com.damk.quasar.model.InfoSatelite;
import com.damk.quasar.model.RespuestaSatelite;

@RestController
public class Controller {
	
	@Autowired
	private TopSecretService topSecretService;
	
	/*
	 * Obtiene la información de una nave de la cual solo se tiene su referencia relativa de posicion
	 * y el mensaje que emitió a los 3 satélites de los cuales están incompletos debido a interferencias
	 * 
	 * Debe retornar la posición actual de la nave y también el mensaje original con el cual se emitió
	 */
	@RequestMapping(method=RequestMethod.POST, value="/topSecret/")
	public ResponseEntity<String> darUbicacionYMensaje(@RequestBody String payload) {
//		System.out.println(payload);
		String respuesta = "";
		try {
			RespuestaSatelite respuestaDeServicio = topSecretService.ejecutarServicio(payload);
			return new ResponseEntity<String>(respuestaDeServicio.toJson(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			respuesta = e.getMessage();
			return new ResponseEntity<String>(respuesta, HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Guarda la información que se le pasa ligada a un satélite en especifico
	 * Buscará sobrescribir la informacion en caso de que exista sino creará uno nuevo.
	 */
	@RequestMapping(method=RequestMethod.POST, value="/topsecret_split/{satellite_name}")
	public ResponseEntity<String> guardarUbicacionYMensajeEmitidoASatelite(@RequestBody String payload, @PathVariable String satellite_name) {
		try {
			topSecretService.guardarDatosDeUbicacionYMensaje(payload, satellite_name);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (ExcepcionSatelite e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Buscará entre la información que se guardó de los satélites y tratará
	 * de obtener la ubicación de la nave y también del mensaje.
	 * Sin embargo, en caso de que el mensaje esté incompleto o que la ubicación
	 * de la nave no se puede obtener, entocncer devolverá un mensaje que
	 * "NO HYA SUFICIENTE INFORMACIÓN"
	 */
	@RequestMapping(method=RequestMethod.GET, value="/topsecret_split/")
	public ResponseEntity<String> obtenerInformacionPorSatelite() {
		try {
			RespuestaSatelite respuestaDeServicio = topSecretService.obtenerUbicacionYDatosAlmacenados();
			return new ResponseEntity<String>(respuestaDeServicio.toJson(), HttpStatus.ACCEPTED);
		} catch (ExcepcionSatelite e) {
			return new ResponseEntity<String>("No hay suficiente información", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping("/")
	public String enviarMensaje() {
		return "prueba";
	}
	

}
