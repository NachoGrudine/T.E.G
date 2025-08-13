import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LobbyComponent } from '../lobby/lobby.component';
import { PreLobbyComponent } from '../pre-lobby/pre-lobby.component';
import {NgIf} from '@angular/common';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-contenedor-lobby',
  templateUrl: './contenedor-lobby.component.html',
  standalone: true,
  imports: [
    LobbyComponent,
    PreLobbyComponent,
    NgIf,
    CommonModule,

  ]
})
export class ContenedorLobbyComponent {
  idPartida!: number;
  idUsuario!: number;
  mostrarPreLobby: boolean = true;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.idPartida = Number(this.route.snapshot.paramMap.get('id'));
    this.idUsuario = Number(localStorage.getItem('userId'));
  }

  alConfirmarJugador() {
    this.mostrarPreLobby = false;
  }
}
