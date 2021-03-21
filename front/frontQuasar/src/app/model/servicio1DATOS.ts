import { DatosYSatelite } from "./datosYSatelite";

export class servicio1Datos{
    "satellites":DatosYSatelite[]

    constructor(data : DatosYSatelite[]){
        this.satellites=data
    }
}