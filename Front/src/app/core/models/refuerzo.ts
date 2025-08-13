
import {PaisJugador} from './paisJugador';

export interface Refuerzo {
  id:number
  idTurnoRef:number
  paisJugadorModel: PaisJugador
  cantidad:number
  tipoFichas:'DE_CANJE' | 'CONTINENTE' | 'TARJETA' | 'EN_CURSO' | 'DE_PAISES'


}
