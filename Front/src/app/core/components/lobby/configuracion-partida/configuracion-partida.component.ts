import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configuracion-partida',
  standalone: true,
  templateUrl: './configuracion-partida.component.html',
  styleUrls: ['./configuracion-partida.component.css'],
  imports: [CommonModule, FormsModule]
})
export class ConfiguracionPartidaComponent {
  @Input() objetivoActual!: number;
  @Input() tipoActual!: string;

  @Output() configuracionGuardada = new EventEmitter<{ objetivo: number, tipo: string }>();
  @Output() cerrar = new EventEmitter<void>();

  objetivo: number = 0;
  tipo: string = 'PUBLICA';

  ngOnInit(): void {
    this.objetivo = this.objetivoActual;
    this.tipo = this.tipoActual;
  }

  guardar(): void {
    this.configuracionGuardada.emit({
      objetivo: this.objetivo,
      tipo: this.tipo
    });
  }

  cerrarModal(): void {
    this.cerrar.emit();
  }
}
