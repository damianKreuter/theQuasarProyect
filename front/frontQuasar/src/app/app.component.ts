import { Component } from '@angular/core';

import { Satelite } from './model/satelite';
import { Datos, DatosAAPi } from './model/datos';
import { ServicioQuasarService } from './services/servicio-quasar.service'
import { DatosYSatelite } from './model/datosYSatelite';
import { servicio1Datos } from './model/servicio1DATOS';
import { error } from '@angular/compiler/src/util';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontQuasar';

  servicio1Status : Boolean = false;
    listaSatelites : Satelite[] = [];
    infoDeSatelite : Datos[] = [];
  
  datosDeSatelites : DatosYSatelite[]=[]

    satelites : DatosYSatelite[] = [];
  
    modificarSatelite(satelite : Satelite){
  
    }
  
    constructor(
      private quasarService: ServicioQuasarService
      ) { 
        this.servicio1Status = false;
        this.obtenerDatosSatelite()
        this.crearInfoDeSatelites();
        this.obtenerDatosDeSatelites();
      }

    crearInfoDeSatelites(){
      this.satelites.push(new DatosYSatelite("kenobi", "100.0", ["este", "", "", "mensaje", ""]))
      this.satelites.push(new DatosYSatelite("skywalker", "100.0", ["", "", "un", "", "secreto"]))
      this.satelites.push(new DatosYSatelite("sato", "100.0", ["", "es", "", "mensaje", ""]))
    }
  
    ngOnInit() {
     // this.mostrarPrecios();
      
    }

    agregarItem(nombre :String, distancia:String, mensajeInterferido:String){
      let array = this.obtenerArrayDeMensaje(mensajeInterferido);
   //   const usingSplit : String[]= algo.split(' ');
      this.satelites.push(new DatosYSatelite(nombre, distancia, array));
    }

    private obtenerArrayDeMensaje(mensaje : String) : String[]{
      let array : String[]=[]
      let palabra : String = ""
      Array.from(mensaje).forEach(function (element){
        if(element=="-"){
          array.push(palabra)
          palabra =""
        } else {
          palabra = palabra+element
        }
      })
      array.push(palabra)
      return array
    }

    vaciarTodo(){
      this.satelites = []
    }

    enviarPeticionDeUbicacion(){
      let datoDeServicioAPasar = new servicio1Datos(this.satelites)
      let json = JSON.stringify(datoDeServicioAPasar)
      this.quasarService.consultaPosicionYMensaheEnviandoDatos(json).subscribe(
        r=>  console.log(JSON.stringify(r)),
        error => console.log('Oops', error.error)
      )
    }

    agregarItemDeDatosSobreNave(nombreSatelite : String, distancia : String, mensajeInterferido : String){
      let array = this.obtenerArrayDeMensaje(mensajeInterferido);
      let newDato = new DatosAAPi(distancia, array)
      this.quasarService.actualizarDatosDeSatelite(nombreSatelite, JSON.stringify(newDato)).subscribe(
        r=>console.log("BIEN, se han actaulizado los datos"),
        error => console.log('Oops', error.error)
      )
      this.obtenerDatosDeSatelites();
    }

    obtenerPosicioNDeNaveYMensajeMedianteMensajesAlmacenados(){
      this.quasarService.obtenerPosicionNaveYMensajePorAlmacenamiento(this.satelites).subscribe(
        r=>console.log(r),
        error=>console.log(error.error)
      )
    }

    obtenerDatosDeSatelites(){
      this.quasarService.obtenerDataDeTransmisiones().subscribe(
        r=> {
          console.log(r);
          this.datosDeSatelites=r
        }
      );
    }

    cerrarAlerta1(){
      this.servicio1Status=false;
    }

    obtenerDatosSatelite(){
      this.listaSatelites = []
      this.quasarService.obtenerPosicionSatelites().subscribe((satelitesDesdeAPI)=>{
        console.log(satelitesDesdeAPI)
        this.listaSatelites = satelitesDesdeAPI
      })
    }
  

    actualizarPosicion(nombre:String, posicionX:String, posicionY:String){
      let satelite :Satelite = new Satelite(nombre, posicionX, posicionY)
      console.log("Satelite a cambiar posicion: "+satelite.nombre + " posicion "+satelite.ubicacion.x + " y "+satelite.ubicacion.y)
      this.quasarService.cambiarPosicionSatelite(satelite).subscribe(
        r=>console.log("Cambios hechos y aceptados"),
        error=>console.log("ERROR: "+ error.error)
      )
   //   console.log("Actualizo posicion de: "+satelite.nombre +" a posicion: "+satelite.ubicacion.x +" y" +satelite.ubicacion.y)
      this.obtenerDatosSatelite()
    }
}
