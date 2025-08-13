import {Reagrupacion} from './reagrupacion';
import {Combate} from './combate';

export interface Accion {
  id:number
  idTurnoAtaque:number
  reagrupacion : Reagrupacion | null
  combate : Combate | null




}
