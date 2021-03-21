import { Injectable } from '@angular/core';
import Swal from 'sweetalert2'

@Injectable({
  providedIn: 'root'
})
export class SweetAlertMensajeService {

  constructor() { }

  mensajeSwall : String = ""

  alertaOK(){
    Swal.fire({
      icon: 'success',
      title: 'OK',
      text: 'La petición fue aceptada',
      footer: 'Refresque la página si no se visualizan los cambios'
    })
  }

  alertaERROR(){
    Swal.fire({
      icon: 'error',
      title: 'Error',
      text: 'No hay suficiente información o es incorrecta'
    })
  }
}
