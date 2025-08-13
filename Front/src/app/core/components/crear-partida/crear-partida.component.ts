import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {PartidasService} from '../../services/partidas.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-crear-partida',
  imports: [FormsModule],
  standalone: true,
  templateUrl: './crear-partida.component.html',
  styleUrl: './crear-partida.component.css'
})
export class CrearPartidaComponent implements OnInit, OnDestroy {



  constructor(private partidasService: PartidasService, private router: Router) { }

  private subscription: Subscription = new Subscription();



  private idUsuarioLogueado!: number;
  public codigoPartida: number | undefined;


  ngOnInit(): void {
    this.idUsuarioLogueado = Number(localStorage.getItem('userId'));
  }

  unirsePartida() {
    if (this.codigoPartida === undefined || isNaN(this.codigoPartida)) {
      alert('Por favor, ingrese un código válido');
      return;
    }

    this.subscription = this.partidasService.getPartidaPorId(this.codigoPartida).subscribe({
      next: (partida) => {
        alert('Partida Encontrada!');
        this.router.navigate(['/lobby', partida.id]);
      },
      error: (error) => {
        alert('Error al buscar partida');
        console.error(error);
      }
    });
  }



  crearPartida() {


    this.subscription = this.partidasService.postPartida(this.idUsuarioLogueado).subscribe({
      next: (partidaCreada) => {
        alert('Partida Creada');
        this.router.navigate(['/lobby', partidaCreada.id]);
      },
      error: (error) => {
        alert('Error al crear partida');
        console.error(error);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
