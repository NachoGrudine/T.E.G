import {FronteraDetalle} from './fronteraDetalle';


export interface PaisDetalle {
  nombrePaisJugador: string;
  fichas: number;
  duenio: string;
  idDuenio:number;
  idPais:number;
  fronteras: FronteraDetalle[];
}
