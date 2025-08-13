import { Component, Input, Output, EventEmitter } from '@angular/core';
import {NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-reagrupacion-modal',
  templateUrl: './reagrupacion-modal.component.html',
  standalone: true,
  imports: [
    NgStyle,
    NgIf
  ],
  styleUrls: ['./reagrupacion-modal.component.css']
})
export class ReagrupacionModalComponent {

  @Input() titulo: string = 'Selecciona cuántas fichas mover';
  @Input() botonTomarTexto: string = 'Tomar';
  @Input() botonCancelarActivo: boolean = true;

  @Input() min: number = 1;
  @Input() max: number = 10;

  @Input() colorFondo: string = '#000';
  @Input() colorTexto: string = '#fff';
  @Input() colorBotonTomar: string = '#ff3d3d';
  @Input() colorBotonCancelar: string = '#0057d9';
  @Input() colorControles: string = '#0057d9';
  @Input() visible = true;

  @Output() tomar = new EventEmitter<number>();
  @Output() cancelar = new EventEmitter<void>();


  cantidad: number = 1;

  ngOnInit() {

  }

  incrementar() {
    if (this.cantidad < this.max) this.cantidad++;
  }

  decrementar() {
    if (this.cantidad > this.min) this.cantidad--;
  }

  onTomar() {
    this.tomar.emit(this.cantidad);

  }

  onCancelar() {
    if (this.botonCancelarActivo) {
      this.visible = false
    }
  }
}
