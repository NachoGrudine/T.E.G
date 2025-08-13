import { Component, Input } from '@angular/core';
import {NgForOf} from '@angular/common';

export interface Tarjeta {
  id: number;
  estado: 'USADA' | 'SIN_USAR';
  nombre: string;
  simbolo: 'BARCO' | 'COMODIN' | 'CANION' | 'GLOBO';
}

@Component({
  selector: 'app-barra-jugador',
  templateUrl: './barra-jugador.component.html',
  imports: [
    NgForOf
  ],
  styleUrls: ['./barra-jugador.component.css']
})
export class BarraJugadorComponent {
  @Input() tarjetas: Tarjeta[] = [];

}
