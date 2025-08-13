import {Continente} from './continente';

export interface ObjetivoCantidadPaisesModel{
  id: number;
  idObjetivo: number;
  continenteModel: Continente;
  cantidad: number;
}
