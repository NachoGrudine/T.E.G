import {Color} from './core/models/color';
import {Objetivo} from './core/models/objetivo';
import {EstadoJugador} from './core/models/estado-jugador';


export const COLOR_DEFAULT: Color = { id: 1, color: 'Rojo' };
export const OBJETIVO_DEFAULT: Objetivo = { id: 10, color: COLOR_DEFAULT };
export const ESTADOJUGADOR_DEFAULT: EstadoJugador = { id: 1, estado: 'ESPERANDO' };
