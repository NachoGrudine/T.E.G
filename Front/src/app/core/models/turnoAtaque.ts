import {Accion} from './accion';

export interface TurnoAtaque {
  id:number
  idJugador:number
  idTarjeta:number
  estado:   'CREADA' | 'EN_PREPARACION' | 'FINALIZADO' | 'EN_CURSO' | 'EN_REFUERZO' | 'EN_ATAQUE' |'SIN_JUGAR'
  acciones: Accion[] | null
}
