import {Component, Input, OnChanges} from '@angular/core';
import {Objetivo} from '../../../../models/objetivo';
import {NgClass, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-objetivo',
  standalone: true,
  imports: [NgIf, NgForOf, NgClass],
  templateUrl: './objetivo.component.html',
  styleUrl: './objetivo.component.css',
})
export class ObjetivoComponent implements OnChanges{
  @Input() objetivo!: Objetivo;

  get objetivosPorCantidad() {
    return this.objetivo?.objetivoCantidadPaisesModels ?? [];
  }

  get objetivosPorContinente() {
    return this.objetivo?.objetivoContModels ?? [];
  }

  ngOnChanges() {
    console.log('Objetivo recibido:', this.objetivo);
  }
  getColorClass(color: string | undefined): string {
    if (!color) return '';
    return 'color-' + color.toLowerCase().replace(/\s/g, '');
  }

}
