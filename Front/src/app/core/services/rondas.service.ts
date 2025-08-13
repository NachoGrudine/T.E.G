import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Partida} from '../models/partida';
import {JugadorCompleto} from '../models/jugadorCompleto';
import {Pais} from '../models/pais';
import {firstValueFrom, Observable} from 'rxjs';
import {Ronda} from '../models/ronda';
import {Jugador} from '../models/jugador';
import {TurnoRefuerzo} from '../models/turnoRefuerzo';
import {Fichas} from '../models/fichas';
import {RefuerzoDTO} from '../models/refuerzoDTO';
import {TurnoAtaque} from '../models/turnoAtaque';
import {PaisJugador} from '../models/paisJugador';
import {AccionCombatePostDTO} from '../models/accionCombatePostDTO';
import {Accion} from '../models/accion';
import {AccionReagrupacionPostDTO} from '../models/ReagrupacionPostDTO';
import {GanadorDTO} from '../models/ganador';



@Injectable({
  providedIn: 'root'
})
export class RondasService {
  private idUsuarioLogueado!: number;

  private API_URL = 'http://localhost:8080/api/partidas';

  constructor(private http: HttpClient) { }

  private partidaActual!: Partida;
  private jugador!: JugadorCompleto;
  private paises!: Pais[];
  private ronda!: Ronda;

  //ronda actual

  async empezarRonda() {

    try {
      const ronda = await firstValueFrom(this.getRonda(this.partidaActual.id, this.jugador.id));
      this.ronda = ronda;
      for (const jugador of this.ronda.jugadores) {
        if (jugador.id === this.jugador.id) {
          this.setJugador(jugador)
          break;
        }
      }

    } catch (error) {
      console.error('Error al obtener la ronda:', error);
    }
  }
  getTurnosAtaque(){
    return this.ronda.turnosAtaque
  }

  getTurnosRefuerzo(){

    return this.ronda.turnosRefuerzo
  }

  getTurnoRefuerzoJugador(idTurno:number){
    return this.getTurnoRefuerzo(idTurno)

  }

  async getFichasTurno(idTurno: number): Promise<Fichas> {
    return await firstValueFrom(this.getFichas(idTurno));
  }


  getJugadores(){

    return this.ronda.jugadores;
  }



  setPartida(partida: Partida) {
    this.partidaActual = partida;
    this.idUsuarioLogueado = Number(localStorage.getItem('userId'));
    for (const jugador of partida.jugadores) {
      if (jugador.idUsuario === this.idUsuarioLogueado) {
        this.setJugador(jugador)
        break;
      }
    }

  }


  getPartida() {
    return this.partidaActual;
  }

  setJugador(jugador: JugadorCompleto) {
    this.jugador = jugador;
  }

 setPaises(paises: Pais[]){
    this.paises = paises;
 }

 getJugador(){
    return this.jugador;
 }

  getPaisesJugador(): Observable<PaisJugador[]> {

    return this.http.get<PaisJugador[]>(`${this.API_URL}/${this.partidaActual.id}/paises`);
  }

  getRonda(id: number, idJugador : number): Observable<Ronda> {
    return this.http.get<Ronda>(`${this.API_URL}/${id}/rondas/ultima/${idJugador}`);
  }

  getTurnoRefuerzo(idTurno: number): Observable<TurnoRefuerzo> {
    return this.http.get<TurnoRefuerzo>(`${this.API_URL}/turnos-refuerzos/${idTurno}`);
  }

  getFichas(idTurno: number): Observable<Fichas> {
    return this.http.get<Fichas>(`${this.API_URL}/turnos-refuerzos/${idTurno}/fichas`);
  }


  enviarRefuerzos(refuerzos: RefuerzoDTO[]): Observable<any> {

    return this.http.post(`${this.API_URL}/turnos-refuerzos/refuerzos`, refuerzos);
  }



  terminarTurno(idTurno: number) {

    this.http.get(`${this.API_URL}/${this.partidaActual.id}/rondas/${this.ronda.id}/turnos-refuerzos/${idTurno}/finalizar`)
      .subscribe({
        next: () => console.log('Turno finalizado correctamente'),
        error: err => console.error('Error al finalizar turno', err)
      });
  }


  getTurnoAtaqueJugador(idTurno: number): Observable<TurnoAtaque> {
    return this.http.get<TurnoAtaque>(`${this.API_URL}/turnos-ataques/${idTurno}`);
  }


  hacerAtaque(accion: AccionCombatePostDTO): Observable<Accion> {
    return this.http.post<Accion>(`${this.API_URL}/turnos-ataque/acciones/combates`, accion);
  }

  hacerReagrupacion(accion: AccionReagrupacionPostDTO): Observable<Accion> {
    return this.http.post<Accion>(`${this.API_URL}/turnos-ataque/acciones/reagrupaciones`, accion);
  }

  terminarTurnoAtk(idTurno: number) {

    this.http.get(`${this.API_URL}/${this.partidaActual.id}/rondas/${this.ronda.id}/turnos-ataques/${idTurno}/finalizar`)
      .subscribe({
        next: () => console.log('Turno finalizado correctamente'),
        error: err => console.error('Error al finalizar turno', err)
      });
  }


  getGanador(id:number): Observable<GanadorDTO> {
    return this.http.get<GanadorDTO>(`${this.API_URL}/ganador/${id}`);
  }



}
