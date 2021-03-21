import { Component } from '@angular/core';

import { Satelite } from './model/satelite';
import { Datos, DatosAAPi } from './model/datos';
import { ServicioQuasarService } from './services/servicio-quasar.service'
import { DatosYSatelite } from './model/datosYSatelite';
import { servicio1Datos } from './model/servicio1DATOS';
import { error } from '@angular/compiler/src/util';
import { SweetAlertMensajeService } from './services/sweet-alert-mensaje.service'

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
    resultadoServicio1 : String = ""
    respuestaServicio3 : String = ""

  datosDeSatelites : DatosYSatelite[]=[]

    satelites : DatosYSatelite[] = [];
  
    modificarSatelite(satelite : Satelite){
  
    }
  
    constructor(
      private quasarService: ServicioQuasarService,
      private sweetAlertService : SweetAlertMensajeService
      ) { 
        this.servicio1Status = false;
        this.obtenerDatosSatelite()
        this.crearInfoDeSatelites();
        this.obtenerDatosDeSatelites();
      }

      /*
        Información preliminar por default, se puede quitar y agregar nuevas
      */
    crearInfoDeSatelites(){
      this.satelites.push(new DatosYSatelite("kenobi", "100.0", ["este", "", "", "mensaje", ""]))
      this.satelites.push(new DatosYSatelite("skywalker", "100.0", ["", "", "un", "", "secreto"]))
      this.satelites.push(new DatosYSatelite("sato", "100.0", ["", "es", "", "mensaje", ""]))
    }
  
    ngOnInit() {
     // this.mostrarPrecios();
      
    }

    /*
      SERVICIO 1
      Agrega un nuevo ITEM para la tabla que muestra los datos a enviar
    */
    agregarItem(nombre :String, distancia:String, mensajeInterferido:String){
      let array = this.obtenerArrayDeMensaje(mensajeInterferido);
   //   const usingSplit : String[]= algo.split(' ');
      this.satelites.push(new DatosYSatelite(nombre, distancia, array));
    }

    /*
      Convierte 1 String en un String[], donde cada elemento pertenece a una palabra
      que son identificados por un separador, en caso de que haya 2 separadores continuos
      significa que hay un desafaje y queda vacia 
    */
    private obtenerArrayDeMensaje(mensaje : String) : String[]{
      let array : String[]=[]
      let palabra : String = ""
      Array.from(mensaje).forEach(function (element){
        if(element==" "){
          array.push(palabra)
          palabra =""
        } else if(element=="-"){
        } else {
          palabra = palabra+element
        }
      })
      array.push(palabra)
      return array
    }

    /*
      Vacia por completo del servicio 1 la lista
    */
    vaciarTodo(){
      this.satelites = []
    }

    /*
      SERVICIO 1
      Envia a la api la información que está contenida para el servicio 1
    */
    enviarPeticionDeUbicacion(){
      let datoDeServicioAPasar = new servicio1Datos(this.satelites)
      let json = JSON.stringify(datoDeServicioAPasar)
      this.quasarService.consultaPosicionYMensaheEnviandoDatos(json).subscribe(
        r=>  {
          console.log(JSON.stringify(r))
          this.resultadoServicio1 = JSON.stringify(r)
          this.sweetAlertService.alertaOK();
        },
        error => {
            this.sweetAlertService.alertaERROR()          
            this.resultadoServicio1 = JSON.stringify(error.error)
          }
        )
    }

    /*
      SERVICIO 3
      Agrega a un satélite la distancia y el mensaje que se quiere trasmitir a ella y la almacena
      en el caso de que sea correcto, sino emite un error.
    */
    agregarItemDeDatosSobreNave(nombreSatelite : String, distancia : String, mensajeInterferido : String){
      let array = this.obtenerArrayDeMensaje(mensajeInterferido);
      let newDato = new DatosAAPi(distancia, array)
      this.quasarService.actualizarDatosDeSatelite(nombreSatelite, JSON.stringify(newDato)).subscribe(
        r=>  {
          console.log(JSON.stringify(r))
          this.sweetAlertService.alertaOK();
        },
        error => {
            console.log('Oops', error.error)
            this.sweetAlertService.alertaERROR()          
          }
      )
      this.obtenerDatosDeSatelites();
    }

    /*
      SERVICIO 3
      Envia una petición para obtener la posición y el mensaje original de la nave
      consultando a los datos almacenados de los satélites a los cuales envió la información
    */
    obtenerPosicioNDeNaveYMensajeMedianteMensajesAlmacenados(){
      this.quasarService.obtenerPosicionNaveYMensajePorAlmacenamiento(this.satelites).subscribe(
        r=>  {
          console.log(JSON.stringify(r))
          this.respuestaServicio3 = JSON.stringify(r)
          this.sweetAlertService.alertaOK();
        },
        error => {
            console.log('Oops', error.error)
            this.respuestaServicio3 = JSON.stringify(error.error)
            this.sweetAlertService.alertaERROR()          
          }
      )
    }

    /*
      SERVICIO 3
      Borar las transmisiones enviadas y guardadas
    */
      vaciarDatosAlmacenados(){
        this.quasarService.vaciarTransmisiones().subscribe(
          r=>  {
            console.log(JSON.stringify(r))
            this.sweetAlertService.alertaOK();
          },
          error => {
            console.log('Oops', error.error)
            this.sweetAlertService.alertaERROR()          
          }
        )
      }

    /*
      SERVICIO 2
      Obtiene todos los datos respecto al posicionamiento de 1 satélite
    */
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

    private obtenerDatosSatelite(){
      this.listaSatelites = []
      this.quasarService.obtenerPosicionSatelites().subscribe((satelitesDesdeAPI)=>{
        console.log(satelitesDesdeAPI)
        this.listaSatelites = satelitesDesdeAPI
      })
    }
  
    /*
      SERVICIO 2
      Envia una petición al BACK en donde intentará actualizar la posición de los satélites que
      aparecen
    */
    actualizarPosicion(nombre:String, posicionX:String, posicionY:String){
      let satelite :Satelite = new Satelite(nombre, posicionX, posicionY)
      console.log("Satelite a cambiar posicion: "+satelite.nombre + " posicion "+satelite.ubicacion.x + " y "+satelite.ubicacion.y)
      this.quasarService.cambiarPosicionSatelite(satelite).subscribe(
        r=>  {
          console.log(JSON.stringify(r))
          this.sweetAlertService.alertaOK();
          this.obtenerDatosSatelite()
        },
        error => {
            console.log('Oops', error.error)
            this.sweetAlertService.alertaERROR()          
          }
      )
   //   console.log("Actualizo posicion de: "+satelite.nombre +" a posicion: "+satelite.ubicacion.x +" y" +satelite.ubicacion.y)
      this.obtenerDatosSatelite()
    }
}
