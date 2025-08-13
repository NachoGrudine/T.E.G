import {Component, Input, Output, EventEmitter, OnChanges, SimpleChanges, OnDestroy} from '@angular/core';
import { PartidasService } from '../../../services/partidas.service';
import { Color } from '../../../models/color';
import { Jugador } from '../../../models/jugador';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-agregar-bot',
  standalone: true,
  templateUrl: './agregar-bot.component.html',
  styleUrls: ['./agregar-bot.component.css'],
  imports: [CommonModule, FormsModule]
})
export class AgregarBotComponent implements OnChanges, OnDestroy {
  @Input() idPartida!: number;
  @Input() visible = false;
  @Output() botAgregado = new EventEmitter<void>();
  @Output() cerrarModal = new EventEmitter<void>();

  coloresDisponibles: Color[] = [];
  idColorSeleccionado: number | null = null;
  dificultadSeleccionada: string | null = null;

  private sub = new Subscription();
  dificultades = ['BOT_NOVATO', 'BOT_BALANCEADO', 'BOT_EXPERTO'];

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

  seleccionarDificultad(dificultad: string): void {
    this.dificultadSeleccionada = dificultad;
  }

  confirmar(): void {
    if (!this.dificultadSeleccionada || this.idColorSeleccionado === null)
      return;

    const bot: Jugador = {
      idUsuario  : null,
      nombre     : this.dificultadSeleccionada,
      idColor    : this.idColorSeleccionado,
      tipoJugador: this.dificultadSeleccionada
    };

    this.partidasService.agregarJugador(this.idPartida, bot).subscribe({
      next : () => {
        this.botAgregado.emit();
        this.resetFormulario();
        this.cerrarModal.emit();
      },
      error: err => console.error('Error al agregar bot:', err)
    });
  }

  cerrar(): void {
    this.resetFormulario();
    this.cerrarModal.emit();
  }

  resetFormulario(): void {
    this.idColorSeleccionado = null;
    this.dificultadSeleccionada = null;
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
