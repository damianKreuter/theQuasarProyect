export class DatosYSatelite {
    satelite : String;
    distance : String;
    mensaje : String[]

    constructor(nombre:String, distancia:String, mensaje:String[]){
        this.satelite=nombre
        this.distance=distancia
        this.mensaje=mensaje
    }
}