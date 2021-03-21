import { Ubicacion } from "./ubicacion";

export class Satelite {
    nombre : String;
    ubicacion : Ubicacion

    constructor(nombre:String, x:String, y :String){
        this.nombre=nombre;
        this.ubicacion = new Ubicacion(x, y);
    }
}