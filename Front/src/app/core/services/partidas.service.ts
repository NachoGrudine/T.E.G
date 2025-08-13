import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Partida } from '../models/partida';
import { Jugador } from '../models/jugador';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {Color} from '../models/color';

@Injectable({
  providedIn: 'root'
})
export class PartidasService {

  private API_URL = 'http://localhost:8080/api/partidas';
  private coloresSubject = new BehaviorSubject<Color[]>([]);
  coloresDisponibles$ = this.coloresSubject.asObservable();

  constructor(private http: HttpClient) { }

  getPartidasPorUsuario(id: number): Observable<Partida[]> {
    return this.http.get<Partida[]>(`${this.API_URL}/usuario/${id}`);
  }

  //crear una partida nueva
  postPartida(idUsuario: number): Observable<Partida> {
    return this.http.post<Partida>(this.API_URL, idUsuario);
  }


//unirse a partida
  getPartidaPorId(id: number): Observable<Partida> {
    return this.http.get<Partida>(`${this.API_URL}/${id}`);
  }

//buscar los colores
  getColoresDisponibles(idPartida: number): Observable<Color[]> {
    return this.http.get<Color[]>(`${this.API_URL}/${idPartida}/colores`);
  }

  cargarColoresDisponibles(idPartida: number): void {
    this.getColoresDisponibles(idPartida).subscribe({
      next : colores => this.coloresSubject.next(colores),
      error: err     => console.error('Error al obtener colores:', err)
    });
  }

  agregarJugador(idPartida: number, jugador: Jugador): Observable<void> {
    return this.http
      .post<void>(`${this.API_URL}/${idPartida}/jugadores`, jugador)
      .pipe(
        tap(() => this.cargarColoresDisponibles(idPartida))  // refresca la lista
      );
  }
//emepxar la partida
  empezarPartida(idPartida: number) {
    return this.http.put(`${this.API_URL}/${idPartida}/empezar`, null);
  }


  actualizarConfiguracion(idPartida : number, config: { objetivo: number, tipo: string } ) {
    return this.http.put(`${this.API_URL}/${idPartida}/configurar`, config );
  }

  getPartidasPublicas(): Observable<Partida[]> {
    return this.http.get<Partida[]>(`${this.API_URL}/publicas`);
  }

}
