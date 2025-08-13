import { Continente } from './continente';

export interface Pais {
  id: number;
  nombre: string;
  continente: Continente;
  idPaisesFronteras: number[];
}
