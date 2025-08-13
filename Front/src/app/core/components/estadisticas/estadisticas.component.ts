import { Component, OnInit, OnDestroy } from '@angular/core';
import { EstadisticasService } from '../../services/estadisticas.service';
import { JsonPipe, KeyValuePipe, NgForOf, NgIf } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';

interface PartidasData {
  jugadas: number;
  ganadas: number;
}

interface ColoresData {
  [key: string]: number;
}

interface ObjetivoData {
  [key: string]: boolean;
}

@Component({
  selector: 'app-estadisticas',
  standalone: true,
  imports: [
    NgIf,
    JsonPipe,
    KeyValuePipe,
    NgForOf
  ],
  templateUrl: './estadisticas.component.html',
  styleUrl: './estadisticas.component.css'
})
export class EstadisticasComponent implements OnInit, OnDestroy {
  partidas: PartidasData | null = null;
  porcentaje: number = 0;
  colores: ColoresData | null = null;
  objetivos: ObjetivoData | null = null;


  private destroy$ = new Subject<void>();

  colorMap: { [key: string]: string } = {
    'Amarillo': '#facc15',
    'Azul': '#3b82f6',
    'Magenta': '#d946ef',
    'Negro': '#1f2937',
    'Rojo': '#ef4444',
    'Verde': '#22c55e'
  };

  constructor(private estadisticasService: EstadisticasService) {}

  ngOnInit(): void {
    const userIdStr = localStorage.getItem('userId');
    const idUsuario = userIdStr ? parseInt(userIdStr, 10) : null;

    if (!idUsuario) {
      console.error('Error: No se encontró usuario logueado en localStorage.');
      return;
    }

    this.estadisticasService.partidasPorUsuario(idUsuario).pipe(
      takeUntil(this.destroy$)
    ).subscribe({
      next: (data: PartidasData) => {
        this.partidas = data;
        if (this.partidas) {
          this.porcentaje = this.calcularPorcentajeVictorias(this.partidas.jugadas, this.partidas.ganadas);
        }
      },
      error: (err) => {
        console.error('Error al cargar partidas:', err);
      }
    });

    this.estadisticasService.porcentajeVictorias(idUsuario).pipe(
      takeUntil(this.destroy$)
    ).subscribe({
      next: (data: number) => {
        this.porcentaje = data;
      },
      error: (err) => {
        console.error('Error al cargar porcentaje de victorias:', err);
      }
    });

    this.estadisticasService.coloresMasUsados().pipe(
      takeUntil(this.destroy$)
    ).subscribe({
      next: (data: ColoresData) => {
        this.colores = data;
      },
      error: (err) => {
        console.error('Error al cargar colores más usados:', err);
      }
    });

    this.estadisticasService.objetivosCumplidos().pipe(
      takeUntil(this.destroy$)
    ).subscribe({
      next: (data: ObjetivoData) => {
        this.objetivos = data;
      },
      error: (err) => {
        console.error('Error al cargar objetivos cumplidos:', err);
      }
    });
  }

  calcularPorcentajeVictorias(jugadas: number, ganadas: number): number {
    if (jugadas === 0) return 0;
    return parseFloat(((ganadas / jugadas) * 100).toFixed(0));
  }

  getColorCode(colorName: string): string {
    return this.colorMap[colorName] || '#cccccc';
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
