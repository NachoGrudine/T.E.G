import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { PartidasService } from '../../services/partidas.service';
import { JugadorCompleto } from '../../models/jugadorCompleto';
import { Partida } from '../../models/partida';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { CommonModule } from '@angular/common';
import {RondasService} from '../../services/rondas.service';
import {AgregarBotComponent} from './agregar-bot/agregar-bot.component';
import {ConfiguracionPartidaComponent} from './configuracion-partida/configuracion-partida.component';


@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  imports: [
    FormsModule,
    NgIf,
    CommonModule,
    AgregarBotComponent,
    ConfiguracionPartidaComponent,

  ],
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit, OnDestroy {

  partida!: Partida;
  jugadores: JugadorCompleto[] = [];
  @Input() idUsuario!: number;
  @Input() idPartida!: number;
  objetivoGlobal: number = 32;
  esCreador: boolean = false;
  @Input() visible!: boolean;
  mostrarAgregarBot = false;
  mostrarConfiguracion = false;




  private intervaloActualizar: any;


  constructor(
    private route: ActivatedRoute,
    private partidasService: PartidasService,
    private rondasService: RondasService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idPartida = Number(this.route.snapshot.paramMap.get('id'));
    this.obtenerPartida();

    this.intervaloActualizar = setInterval(() => {
        this.obtenerPartida();
      }, 1000);

  }

  ngOnDestroy(): void {

    if (this.intervaloActualizar) {
      clearInterval(this.intervaloActualizar);
    }
  }

  obtenerPartida(): void {
    this.partidasService.getPartidaPorId(this.idPartida).subscribe((partida: Partida) => {
      this.partida = partida;
      this.jugadores = partida.jugadores;
      this.esCreador = partida.idUsuario === this.idUsuario;

      if (this.partida.estado === "EN_CURSO") {
        this.rondasService.setPartida(this.partida);
        this.router.navigate(['/pantalla-inicial', this.partida]);
      }
    });
  }


  mostrarTipoPartida(): string {
    switch (this.partida.tipoPartida) {
      case 'PUBLICA': return 'Publica';
      case 'PRIVADA': return 'Privada';
      default: return 'Sin definir';
    }
  }

  iniciarPartida() {
    this.partidasService.empezarPartida(this.partida.id).subscribe({
      next: () => {
        console.log("Partida iniciada correctamente");

      },
      error: (err) => {
        console.error("Error al iniciar la partida", err);

      }
    });
  }


  actualizarConfiguracion(config: { objetivo: number, tipo: string }) {
    this.objetivoGlobal = config.objetivo;

    this.partidasService.actualizarConfiguracion(this.idPartida, config).subscribe({
      next: () => {
        this.obtenerPartida();
        this.mostrarConfiguracion = false;
      },
      error: (err) => console.error('Error al guardar configuración', err)
    });
  }

  mapColorNombreToCss(nombre?: string): string {
    const mapa: Record<string, string> = {
      rojo: 'red', verde: 'green', azul: 'blue', amarillo: 'yellow',
      naranja: 'orange', negro: 'black', blanco: 'white',
      magenta: 'purple', gris: 'gray', celeste: 'skyblue', rosa: 'pink'
    };
    return nombre ? mapa[nombre.toLowerCase()] ?? 'gray' : 'transparent';
  }
  mapColorSuave(nombre?: string): string {
    const color = this.mapColorNombreToCss(nombre);
    /* usa la función CSS color‑mix – compatible en navegadores modernos */
    return `color-mix(in srgb, ${color} 25%, white)`;
  }

}

