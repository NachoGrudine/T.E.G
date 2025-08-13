import {JugadorCompleto} from './jugadorCompleto';
import {TurnoRefuerzo} from './turnoRefuerzo';
import {TurnoAtaque} from './turnoAtaque';


export interface Ronda {
  id:number
  idPartida: number
  numero: number
  estado: 'CREADA' | 'EN_PREPARACION' | 'FINALIZADO' | 'EN_CURSO' | 'EN_REFUERZO' | 'EN_ATAQUE'
  jugadores: JugadorCompleto[];
  turnosRefuerzo: TurnoRefuerzo[] | null
  turnosAtaque: TurnoAtaque[] | null



}
