import {Component, OnDestroy, OnInit} from '@angular/core';
import {PartidasService} from '../../services/partidas.service';
import {Subscription} from 'rxjs';
import {Partida} from '../../models/partida';
import {FormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import {TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-historial-partidas',
  imports: [
    FormsModule,
    TitleCasePipe,

  ],
  templateUrl: './historial-partidas.html',
  styleUrl: './historial-partidas.component.css'
})
export class HistorialPartidas implements OnInit , OnDestroy {

  constructor(private partidasService : PartidasService, private router: Router) {
  }

  private subscription : Subscription = new Subscription();
  partidasPorUsuario: Partida[] = [];
  private idUsuarioLogueado!: number;

  ngOnInit(): void {
    this.idUsuarioLogueado = Number(localStorage.getItem('userId'));
    this.getPartidaPorUsuarios();
  }

  getPartidaPorUsuarios(){
    this.subscription = this.partidasService.getPartidasPorUsuario(this.idUsuarioLogueado).subscribe({
      next:  (data: Partida[]) => {
        this.partidasPorUsuario = data;
      },
      error: (error) => {
        alert(error);
      }
    })
  }
  abrirLobby(idPartida:number){
    this.router.navigate(['/lobby', idPartida]);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
