export class Fichas{
  fichasPais: number;
  fichasTarjeta: Map<number, number>;
  fichasContinente: Map<number, number>;

  constructor() {
    this.fichasPais = 0;
    this.fichasTarjeta = new Map<number, number>(); //idPaisJugador:cantidadFichas
    this.fichasContinente = new Map<number, number>();//idContinente:cantidadFichas
  }
}
