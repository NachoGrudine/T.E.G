import { JugadorCompleto } from './jugadorCompleto';

export interface Partida {
  id: number;
  estado: 'CREADA' | 'EN_PREPARACION' | 'FINALIZADO' | 'EN_CURSO';
  idUsuario: number;
  cantidadParaGanar: number;
  tipoPartida: 'PUBLICA' | 'PRIVADA' ;
  jugadores: JugadorCompleto[];
}
