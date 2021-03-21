import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CATCH_ERROR_VAR } from '@angular/compiler/src/output/output_ast';
import { Injectable } from '@angular/core';
import { RouteConfigLoadStart } from '@angular/router';
import { Observable } from 'rxjs';
import { DatosYSatelite } from '../model/datosYSatelite';
import { Satelite } from '../model/satelite';

@Injectable({
  providedIn: 'root'
})
export class ServicioQuasarService {

  constructor(private http : HttpClient) { }
  
  url  = "https://operacionfuegodequasarml.herokuapp.com/"


  servicioSatelites  = "/coordenadas/"
  servicioConsultaEnviandoDatos = "/topsecret/"
  servicioConsultaADatosAlmacenados =  "/topsecret_split/"
  
  obtenerPosicionSatelites() : Observable<Satelite[]>{
    return this.http.get<Satelite[]>(this.servicioSatelites)
  }
  
  cambiarPosicionSatelite(sateliteAModificar : Satelite) : Observable<String>{
    let urls = this.servicioSatelites+sateliteAModificar.nombre
    console.log(urls)
    return this.http.post<String>(urls, sateliteAModificar.ubicacion)
  }

  consultaPosicionYMensaheEnviandoDatos(data:String) : Observable<any> {
    return this.http.post(this.servicioConsultaEnviandoDatos, data)
  }

  actualizarDatosDeSatelite(nombre : String, dataJson : String) : Observable<any>{
    return this.http.post(this.servicioConsultaADatosAlmacenados+nombre, dataJson)
  }
  
  obtenerDataDeTransmisiones() : Observable<DatosYSatelite[]>{
    return this.http.get<DatosYSatelite[]>("/topsecret_split/data")
  }

  obtenerPosicionNaveYMensajePorAlmacenamiento(data : DatosYSatelite[]) : Observable<any>{
    let json = JSON.stringify(data)
    return this.http.get<any>(this.servicioConsultaADatosAlmacenados)
  }
}
