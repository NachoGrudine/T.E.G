import {Color} from './color';
import {ObjetivoCantidadPaisesModel} from './objetivoCantPaises';
import {ObjetivoContModel} from './objetivoCont';



export interface Objetivo {
  idObjetivo: number;
  colorModel: Color | null;
  objetivoContModels: ObjetivoContModel[];
  objetivoCantidadPaisesModels: ObjetivoCantidadPaisesModel[];
}
