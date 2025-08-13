import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PaisDetalle} from '../../../models/paisDetalle';
import {InfoPaisComponent} from './info-pais/info-pais.component';


import {InfoTurnoComponent} from './info-turno/info-turno.component';
import {NgIf} from '@angular/common';
import {ObjetivoComponent} from './objetivo/objetivo.component';

import {Objetivo} from '../../../models/objetivo';

@Component({
  selector: 'app-sidebar',
  imports: [

    InfoPaisComponent,
    InfoTurnoComponent,
    NgIf,
    ObjetivoComponent
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  @Input() paisDetalle?: PaisDetalle
  @Input() infoTurno?: {
    texto1: string;
    texto2: string;
    texto3: string;
    texto4: string;
  };
  seccion: string = 'pais';

  @Input() idJugador = 0;
  @Input() puedeAtacar = false;
  @Input() puedeReagrupar = false;

  @Input() objetivo?: Objetivo | null;
  @Output() atacar = new EventEmitter<{ idPaisDef: number; idPaisAtk: number }>();
  @Output() reagrupar = new EventEmitter<{ idPaisDestino: number; idPaisOrigen: number }>();


  onAtaque(datos: { idPaisDef: number; idPaisAtk: number }) {
    this.atacar.emit(datos);
  }

  onReagrupar(datos: { idPaisDestino: number; idPaisOrigen: number }) {
    this.reagrupar.emit(datos);
  }





}
