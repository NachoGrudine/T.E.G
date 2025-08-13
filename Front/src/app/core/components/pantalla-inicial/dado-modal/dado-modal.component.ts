import { Component, Input, OnInit } from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-dado-modal',
  templateUrl: './dado-modal.component.html',
  imports: [
    NgForOf,
    NgIf,
    NgClass
  ],
  styleUrls: ['./dado-modal.component.css']
})
export class DadoModalComponent implements OnInit {
  @Input() atacanteNombre: string = '';
  @Input() defensorNombre: string = '';
  @Input() atacanteColor: string = 'red';
  @Input() defensorColor: string = 'blue';
  @Input() atacantePais: string = '';
  @Input() defensorPais: string = '';
  @Input() dadosAtacante: number[] = [];
  @Input() dadosDefensor: number[] = [];

  visible: boolean = true;

  ngOnInit() {
    this.visible = true;
  }

  cerrarModal() {
    this.visible = false;
  }
}
