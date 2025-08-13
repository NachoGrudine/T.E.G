import {Component, Input, OnInit, Output, EventEmitter, OnChanges, SimpleChanges, OnDestroy} from '@angular/core';
import { Color } from '../../models/color';
import { PartidasService } from '../../services/partidas.service';
import { Jugador } from '../../models/jugador';
import { FormsModule } from '@angular/forms';
import { NgIf, NgStyle } from '@angular/common';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-pre-lobby',
  standalone: true,
  templateUrl: './pre-lobby.component.html',
  imports: [
    CommonModule,
    FormsModule,
    NgStyle,
    NgIf,
    NgFor
  ],
  styleUrls: ['./pre-lobby.component.css']
})
export class PreLobbyComponent implements OnInit, OnChanges, OnDestroy {
  @Input() idPartida!: number;
  @Input() idUsuario!: number;
  @Input() visible = false;

  coloresDisponibles: Color[] = [];
  nombreJugador = '';
  idColorSeleccionado: number | null = null;

  private sub = new Subscription();
  @Output() jugadorAgregado = new EventEmitter<void>();
  @Output() cerrarModal = new EventEmitter<void>();

  constructor(private partidasService: PartidasService) {}

  ngOnInit(): void {
    this.sub.add(
      this.partidasService.coloresDisponibles$
        .subscribe(cs => (this.coloresDisponibles = cs))
    );

    if (this.idPartida) {
      this.partidasService.cargarColoresDisponibles(this.idPartida);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['idPartida'] && this.idPartida) {
      this.partidasService.cargarColoresDisponibles(this.idPartida);
    }
    if (
      changes['visible'] &&
      changes['visible'].currentValue === true &&
      this.idPartida
    ) {
      this.partidasService.cargarColoresDisponibles(this.idPartida);
    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  seleccionarColor(idColor: number): void {
    this.idColorSeleccionado = idColor;
  }

  confirmar(): void {
    if (!this.nombreJugador.trim() || this.idColorSeleccionado === null) return;

    const nuevoJugador: Jugador = {
      idUsuario: this.idUsuario,
      nombre: this.nombreJugador.trim(),
      idColor: this.idColorSeleccionado,
      tipoJugador: "HUMANO"
    };

    this.partidasService.agregarJugador(this.idPartida, nuevoJugador).subscribe({
      next: () => {
        this.jugadorAgregado.emit();
        this.resetFormulario();
        this.cerrarModal.emit();
      },
      error: (err) => console.error('Error al agregar jugador:', err)
    });
  }

  resetFormulario(): void {
    this.nombreJugador = '';
    this.idColorSeleccionado = null;
  }

  cerrar(): void {
    this.resetFormulario();
    this.cerrarModal.emit();
  }

  colorYaElegido(colorId: number): boolean {

    return !this.coloresDisponibles.some(c => c.id === colorId);
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
      magenta: 'purple',
      gris: 'gray',
      celeste: 'skyblue',
      rosa: 'pink'

    };
    return mapa[colorNombre.toLowerCase()] || 'gray';
  }
}
