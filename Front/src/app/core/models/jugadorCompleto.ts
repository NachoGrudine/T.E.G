import { Color } from './color';
import { Objetivo } from './objetivo';
import {PaisJugador} from './paisJugador';
import {Tarjeta} from './tarjeta';

export interface JugadorCompleto {
  id: number;
  idPartida: number;
  tipoJugador: 'HUMANO' | 'BOT_NOVATO' | 'BOT_BALANCEADO' | 'BOT_EXPERTO';
  nombre: string;
  color: Color | null;
  objetivo: Objetivo | null;
  estadoJugador: 'VIVO' | 'MUERTO' ;
  idUsuario: number | null;
  idJugadorAsesino: number | null;
  tarjetas: Tarjeta[] | null;
  paises: PaisJugador[];
}

