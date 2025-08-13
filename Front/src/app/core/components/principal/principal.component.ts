import { Component } from '@angular/core';
import {RouterModule, RouterOutlet} from '@angular/router';
import {HistorialPartidas} from '../historial-partidas/historial-partidas';
import {CrearPartidaComponent} from '../crear-partida/crear-partida.component';
import {MapaComponent} from '../pantalla-inicial/mapa/mapa.component';
import {PartidasPublicasComponent} from '../partidas-publicas/partidas-publicas.component';


@Component({
  selector: 'app-principal',
  imports: [RouterModule, CrearPartidaComponent, HistorialPartidas, MapaComponent, PartidasPublicasComponent],
  templateUrl: './principal.component.html',
  styleUrl: './principal.component.css'
})
export class PrincipalComponent {
  usuario = localStorage.getItem('user');
}
