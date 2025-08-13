import { Component, OnInit } from '@angular/core';
import { PartidasService } from '../../services/partidas.service';
import { Partida } from '../../models/partida';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-partidas-publicas',
  imports: [FormsModule],
  templateUrl: './partidas-publicas.component.html',
  styleUrl: './partidas-publicas.component.css'
})
export class PartidasPublicasComponent implements OnInit {
  partidasPublicas: Partida[] = [];

  constructor(private partidasService: PartidasService, private router: Router) {}

  ngOnInit(): void {
    this.partidasService.getPartidasPublicas().subscribe({
      next: (data: Partida[]) => {
        this.partidasPublicas = data;
      },
      error: (error) => {
        alert(error);
      }
    });
  }

  abrirLobby(idPartida: number) {
    this.router.navigate(['/lobby', idPartida]);
  }
}
