
import { Component } from '@angular/core';
import {NgForOf, NgIf, NgStyle} from '@angular/common';

interface Notificacion {
  mensaje: string;
  colorFondo: string;
  timeoutId?: any;
}

@Component({
  selector: 'app-notificaciones',
  templateUrl: './notificaciones.component.html',
  imports: [
    NgIf,
    NgStyle,
    NgForOf
  ],
  styleUrls: ['./notificaciones.component.css']
})

export class NotificacionesComponent {

  notificacionesActivas: Notificacion[] = [];
  historial: Notificacion[] = [];
  verHistorial = false;




  agregar(mensaje: string, colorFondo: string = '#222') {
    const noti: Notificacion = {mensaje, colorFondo};
    this.notificacionesActivas.push(noti);
    this.historial.push(noti);

    if (!this.verHistorial) {
      noti.timeoutId = setTimeout(() => {
        const index = this.notificacionesActivas.indexOf(noti);
        if (index !== -1 && !this.verHistorial) {
          this.notificacionesActivas.splice(index, 1);
        }
      }, 3000);
    }
  }

  toggleHistorial() {
    this.verHistorial = !this.verHistorial;

    if (this.verHistorial) {

      this.notificacionesActivas.forEach(noti => {
        if (noti.timeoutId) {
          clearTimeout(noti.timeoutId);
          noti.timeoutId = null;
        }
      });
    }

    setTimeout(() => {
      const cont = document.getElementById('historial-scroll');
      cont?.scrollTo({top: cont.scrollHeight});
    });
  }


}
