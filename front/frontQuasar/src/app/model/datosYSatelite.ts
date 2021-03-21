export class DatosYSatelite {
    name : String;
    distance : String;
    message : String[]

    constructor(nombre:String, distancia:String, mensaje:String[]){
        this.name=nombre
        this.distance=distancia
        this.message=mensaje
    }
}