import { Component, Input } from '@angular/core';
import {NgForOf, NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-turnos',
  templateUrl: './turnos.component.html',
  imports: [
    NgStyle,
    NgForOf,
    NgIf
  ],
  styleUrls: ['./turnos.component.css']
})
export class TurnosComponent {
  @Input() jugadores: { nombre: string, color: string }[] = [];
  turnoActual: number = 0;

  animando: boolean = false;

  avanzarTurno(): void {
    const jugadores = document.querySelectorAll('.jugador');
    const actualElement = jugadores[this.turnoActual];

    if (actualElement) {
      actualElement.classList.add('avanzando');
      setTimeout(() => {
        actualElement.classList.remove('avanzando');
        this.turnoActual = (this.turnoActual + 1) % this.jugadores.length;
      }, 400);
    } else {
      this.turnoActual = (this.turnoActual + 1) % this.jugadores.length;
    }
  }

  reiniciarRonda(): void {
    this.turnoActual = 0;
  }



}
