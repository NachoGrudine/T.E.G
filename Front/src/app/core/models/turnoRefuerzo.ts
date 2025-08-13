import {JugadorCompleto} from './jugadorCompleto';
import {Refuerzo} from './refuerzo';
import {Canje} from './canje';

export interface TurnoRefuerzo {
  jugadores: JugadorCompleto[];
  id : number
  idJugador : number
  estado:  'CREADA' | 'EN_PREPARACION' | 'FINALIZADO' | 'EN_CURSO' | 'EN_REFUERZO' | 'EN_ATAQUE' | 'SIN_JUGAR'
  refuerzoModels : Refuerzo[] | null
  canjeModels: Canje[] | null


}
