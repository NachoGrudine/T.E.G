import { Component, Input } from '@angular/core';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-versus-modal',
  templateUrl: './versus-modal.component.html',
  imports: [
    NgIf
  ],
  styleUrls: ['./versus-modal.component.css']
})
export class VersusModalComponent {
  @Input() textoArriba = '';
  @Input() colorArriba = '#fff';

  @Input() textoMedio = '';
  @Input() colorMedio = '#fff';

  @Input() textoAbajo = '';
  @Input() colorAbajo = '#fff';

  @Input() visible = false;


  onFondoClick() {
    this.visible = false;
  }
}
