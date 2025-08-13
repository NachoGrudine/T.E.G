import {Component, EventEmitter, Input, Output} from '@angular/core';
import {PaisDetalle} from '../../../../models/paisDetalle';
import {NgForOf, NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-info-pais',
  imports: [
    NgIf,
    NgForOf,
    NgStyle
  ],
  templateUrl: './info-pais.component.html',
  styleUrl: './info-pais.component.css'
})
export class InfoPaisComponent {
  @Input() paisDetalle?: PaisDetalle;
  @Input() idJugador!: number;
  @Input() puedeAtacar = false;
  @Input()  puedeReagrupar = false;

  @Output() atacar = new EventEmitter<{ idPaisDef: number; idPaisAtk: number }>();
  @Output() reagrupar = new EventEmitter<{ idPaisDestino: number; idPaisOrigen: number }>();


  atacarPais(idPais:number) {
    this.puedeAtacar = false;
    const idPaisDef = idPais;
    if(this.paisDetalle){
      const idPaisAtk = this.paisDetalle.idPais;
      this.atacar.emit({ idPaisDef, idPaisAtk });
    }



  }


  reagruparPais(idPais:number) {
    this.puedeReagrupar = false;
    const idPaisDestino = idPais;
    if(this.paisDetalle){
      const idPaisOrigen = this.paisDetalle.idPais;
      this.reagrupar.emit({ idPaisDestino, idPaisOrigen });
    }



  }






  openedIndices: Set<number> = new Set();

  toggle(index: number) {
    if (this.openedIndices.has(index)) {
      this.openedIndices.delete(index);
    } else {
      this.openedIndices.add(index);
    }
  }

  isOpen(index: number): boolean {
    return this.openedIndices.has(index);
  }


  mapColorNombreToCss(colorNombre: string): string {
    const mapa: Record<string, string> = {
      rojo: 'red',
      verde: 'green',
      azul: 'blue',
      amarillo: 'yellow',
      naranja: 'orange',
      negro: 'black',
      blanco: 'white',
      magenta: 'magenta',
      gris: 'gray',
      celeste: 'skyblue',
      rosa: 'pink'

    };
    return mapa[colorNombre.toLowerCase()] || 'gray';
  }

}
