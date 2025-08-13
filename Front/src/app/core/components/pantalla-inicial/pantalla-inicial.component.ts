import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {SidebarComponent} from './sidebar/sidebar.component';
import {MapaComponent} from './mapa/mapa.component';
import {RondasService} from '../../services/rondas.service';
import {JugadorCompleto} from '../../models/jugadorCompleto';
import {TurnoRefuerzo} from '../../models/turnoRefuerzo';
import {firstValueFrom} from 'rxjs';
import {PaisDetalle} from '../../models/paisDetalle';
import {PaisesJugadoresService} from '../../services/paisesJugadores.service';
import {Fichas} from '../../models/fichas';
import {VersusModalComponent} from './modal/versus-modal.component';

import {RefuerzoDTO} from '../../models/refuerzoDTO';
import {BarraJugadorComponent} from './barra-jugador/barra-jugador.component';
import {Tarjeta} from '../../models/tarjeta';
import {AudioService} from '../../services/audio.service';
import {TurnoAtaque} from '../../models/turnoAtaque';
import {DadoModalComponent} from './dado-modal/dado-modal.component';
import {NgIf} from '@angular/common';
import {Pais} from '../../models/pais';
import {Accion} from '../../models/accion';
import { TurnosComponent} from './turnos/turnos.component';
import {NotificacionesComponent} from './notificaciones/notificaciones.component';
import {Color} from '../../models/color';
import {Objetivo} from '../../models/objetivo';
import {PaisJugador} from '../../models/paisJugador';
import {AccionCombatePostDTO} from '../../models/accionCombatePostDTO';
import {ReagrupacionModalComponent} from './reagrupacion-modal/reagrupacion-modal.component';
import {AccionReagrupacionPostDTO} from '../../models/ReagrupacionPostDTO';
import {Router} from '@angular/router';





@Component({
  selector: 'app-pantalla-inicial',
  templateUrl: './pantalla-inicial.component.html',
  imports: [
    MapaComponent,
    SidebarComponent,
    VersusModalComponent,
    BarraJugadorComponent,

    DadoModalComponent,
    NgIf,
    TurnosComponent,
    NotificacionesComponent,
    ReagrupacionModalComponent,


  ],
  styleUrls: ['./pantalla-inicial.component.css']
})
export class PantallaInicialComponent implements AfterViewInit {

  @ViewChild(MapaComponent) mapaComponent!: MapaComponent;
  partida: any;
  jugadores!: JugadorCompleto[]
  jugador!: JugadorCompleto

  paises!: Pais[]

  //refuerzo
  turnosRefuerzo!: TurnoRefuerzo[] | null
  turnoRefuerzoActual!: TurnoRefuerzo
  fichasTurno!: Fichas;

  //Ataque
  turnosAtaque!: TurnoAtaque[] | null
  turnoAtaqueActual!: TurnoAtaque


  private resolverConfirmacion!: () => void;
  //boton
  botonDeshabilitado = true;
  faseRonda!: "REF" | "ATK" | "REAG"
  faseActual!: string;
  textoBoton = 'Terminar refuerzos';

  //modal
  mostrarVersus = false;
  textoArriba = '';
  colorArriba = '#fff';
  textoMedio = '';
  colorMedio = '#fff';
  textoAbajo = '';
  colorAbajo = '#fff';


  //sideBar
  infoTurno = {
    texto1: 'Preparando partida:',
    texto2: 'Repartiendo paises',
    texto3: '...',
    texto4: '...'
  };



  tarjetas: Tarjeta[] = [

  ];


//dados
  atacanteNombre: string = '';
  atacanteColor: string = 'red';
  atacantePais: string = '';

  defensorNombre: string = '';
  defensorColor: string = 'blue';
  defensorPais: string = '';

  dadosAtacante: number[] = [];
  dadosDefensor: number[] = [];

  mostrarDados: boolean = false;

  //turnos
  @ViewChild(TurnosComponent) turnosComponent!: TurnosComponent;
  jugadoresTurno = [
    { nombre: 'Rojo', color: 'red' }
  ];

//notificaciones
  @ViewChild('notiRef') notiRef!: NotificacionesComponent;

  puedeReagrupar = false;
  idsReagrupados: number[] = [];
  puedeAtacar = false;
  jugadorID = 0;



  //modal reagrupar
  mostrarModalReagrupar = false;
  idPaisReagrupar= 0;
  idPaisOrigen= 0;


  titulo:string = ""
  botonTomarTexto :string = ""
  botonCancelarActivo :boolean = true
  min:number = 0;
  max:number = 0;
  colorFondo:string= ""
  colorTexto:string= ""
  colorBotonTomar:string= ""
  colorBotonCancelar:string= ""
  colorControles:string= ""



  constructor(private router: Router, private rondasService: RondasService, private paisesJugadoresService: PaisesJugadoresService,private audioService: AudioService) {
  }






  ngAfterViewInit(): void {
    this.partida = this.rondasService.getPartida();

    if (this.partida && this.mapaComponent) {
      setTimeout(() => {
        this.ponerPaises().then(() => {


          this.rondasService.setPaises(this.mapaComponent.getPaises())
          this.empezarRonda().then(() => {
              console.log('Todos los refuerozs');
            }
          )

        });
      }, 400);



    }
  }







  async empezarRonda() {
    while(this.partida.estado != "FINALIZADO"){

      this.mostrarModalVersus(
        'COMIENZA', "gold",
        'UNA NUEVA', "gold",
        'RONDA', "gold",
        5000
      );
      await this.delay(6000);

      this.botonDeshabilitado = true;
      await this.rondasService.empezarRonda();

      this.jugador = this.rondasService.getJugador();
      this.jugadorID = this.jugador.id;
      this.jugadores = this.rondasService.getJugadores();
      this.jugadoresTurno = this.jugadores.map(jugador => ({
        nombre: jugador.nombre,
        color: this.mapColorNombreToCss(jugador.color?.color ?? '')
      }));


      if(this.jugador.tarjetas != null){
        this.tarjetas = this.jugador.tarjetas

      }


      this.turnosRefuerzo = this.rondasService.getTurnosRefuerzo();
      if (!this.turnosRefuerzo) return;
      this.faseRonda = "REF"

      for (const turno of this.turnosRefuerzo) {
        this.turnoRefuerzoActual = turno;

        if (turno.idJugador !== this.jugador.id || turno.estado == "FINALIZADO") {
          await this.jueganLosDemas(turno)
        }
        else {
          await this.juegaElJugador(turno)
        }
        this.turnosComponent.avanzarTurno()
      }


      //ataques
      this.faseRonda = "ATK"
      this.turnosAtaque = this.rondasService.getTurnosAtaque();

      if (!this.turnosAtaque) return;

      for (const turnoAtk of this.turnosAtaque) {
        this.turnoAtaqueActual = turnoAtk;

        if (turnoAtk.idJugador !== this.jugador.id || turnoAtk.estado == "FINALIZADO" ) {

          await this.jueganLosDemasAtk(turnoAtk)
        }
        else {

          await this.juegaElJugadorAtk(turnoAtk)
        }
        let ganador = await firstValueFrom(this.rondasService.getGanador(turnoAtk.idJugador));
        this.idsReagrupados = []
        this.puedeAtacar = false;
        this.puedeReagrupar = false;
        if (ganador.idJugador != 0){
          let jugador = this.buscarJugador(ganador.idJugador)
          let color = "gold"

          if(jugador){
            if(jugador.color != null){
              color = this.mapColorNombreToCss(jugador.color.color)
            }

            this.mostrarModalVersus(
            'Ganador | Winner | Ganador', color,
            jugador.nombre, color,
            'Ganador | Winner | Ganador', color,
            5000
          );}

          await this.delay(5000)
          await this.router.navigate(['/principal']);



        }

        this.turnosComponent.avanzarTurno()
      }

    }

    this.mostrarModalVersus(
      'Terminó', "white",
      'la ', "white",
      'partida', "white",
      5000
    );
  }



  async actualizarTodos(){
    await this.rondasService.empezarRonda();
    this.jugadores = this.rondasService.getJugadores();
    for (const jugador of this.jugadores) {

      for (const pais of jugador.paises) {
        if(jugador.color){
          this.mapaComponent.setearColor(jugador.color.color.toLowerCase(), pais.idPais);
          this.mapaComponent.setearFichas(pais.idPais,pais.fichas)
        }


      }
    }
  }


  async juegaElJugador(turno:TurnoRefuerzo){
    await this.actualizarTodos()
    let color = "gold"
    if(this.jugador.color != null){
      color = this.mapColorNombreToCss(this.jugador.color.color)
    }
    this.audioService.playSonidoRefuerzo();
    this.mostrarModalVersus(
      'Comienza', color,
      'tu turno de', color,
      'refuerzo', color,
      5000
    );


    this.fichasTurno = await this.rondasService.getFichasTurno(turno.id);


    //las fichas de tarjetass
    this.faseActual = 'DE_TARJETA';
    if(this.fichasTurno.fichasTarjeta != null){
      await this.ponerFichasTarjeta(this.fichasTurno.fichasTarjeta)
      await this.esperarConfirmacionJugador();
      this.mapaComponent.deshabilitarTodos();
    }

    //todas las de cont
    this.faseActual = 'DE_CONTINENTE';
    if(this.fichasTurno.fichasContinente != null){
      for (const [clave, valor] of this.fichasTurno.fichasContinente) {
        await this.ponerFichasContinente(clave, valor)
      }
      await this.esperarConfirmacionJugador();
      this.mapaComponent.deshabilitarTodos();
    }

    //Las de paises
    this.faseActual = 'DE_PAISES';
    await this.ponerFichasLibres(this.fichasTurno.fichasPais);
    await this.esperarConfirmacionJugador();
    this.mapaComponent.deshabilitarTodos();


    this.rondasService.terminarTurno(turno.id)

  }





  async jueganLosDemas( turno: TurnoRefuerzo){
    this.turnoRefuerzoActual= await firstValueFrom(this.rondasService.getTurnoRefuerzoJugador(turno.id));
    await this.delay(2000);
    await this.aplicarPrimerRefuerzo(this.turnoRefuerzoActual);

    while (this.turnoRefuerzoActual.estado !== 'FINALIZADO') {
      await this.delay(2000);
      const turnoActualizado = await firstValueFrom(this.rondasService.getTurnoRefuerzoJugador(turno.id));
      await this.aplicarRefuerzo(turnoActualizado);
      this.turnoRefuerzoActual = turnoActualizado;
    }
  }



  async juegaElJugadorAtk(turno:TurnoAtaque){
    await this.actualizarTodos()
    let color = "red"
    if(this.jugador.color != null){
      color = this.mapColorNombreToCss(this.jugador.color.color)
    }
    this.audioService.playSonidoRefuerzo();
    this.mostrarModalVersus(
      'Comienza', color,
      'tu turno de', color,
      'Ataque', color,
      5000
    );

    //atacar
    this.actualizarTextoBoton("Pasar a reagrupar")
    this.botonDeshabilitado = false;
    await this.esperarConfirmacionJugador();




    this.idsReagrupados = []
    this.faseRonda = "REAG"


    this.botonDeshabilitado = false;
    this.actualizarTextoBoton("Terminar Turno")
    await this.esperarConfirmacionJugador();
    this.botonDeshabilitado = true;


    this.rondasService.terminarTurnoAtk(turno.id)

  }





  async jueganLosDemasAtk( turno: TurnoAtaque){
    this.turnoAtaqueActual= await firstValueFrom(this.rondasService.getTurnoAtaqueJugador(turno.id));
    await this.aplicarPrimerAtaque(this.turnoAtaqueActual);

    while (this.turnoAtaqueActual.estado !== 'FINALIZADO') {
      await this.delay(2000);
      const turnoActualizado = await firstValueFrom(this.rondasService.getTurnoAtaqueJugador(turno.id));
      await this.aplicarAtaques(turnoActualizado);
      this.turnoAtaqueActual = turnoActualizado;
    }
  }


  mostrarModalVersus(
    textoArriba: string, colorArriba: string,
    textoMedio: string, colorMedio: string,
    textoAbajo: string, colorAbajo: string,
    duracionMs: number = 3000
  ) {
    this.textoArriba = textoArriba;
    this.colorArriba = colorArriba;

    this.textoMedio = textoMedio;
    this.colorMedio = colorMedio;

    this.textoAbajo = textoAbajo;
    this.colorAbajo = colorAbajo;

    this.mostrarVersus = true;

    setTimeout(() => {
      this.mostrarVersus = false;
    }, duracionMs);
  }



  async mostrarModalDados(
    atacanteNombre: string, atacanteColor: string, atacantePais: string,
    defensorNombre: string, defensorColor: string, defensorPais: string,
    dadosAtacante: number[], dadosDefensor: number[],
    duracionMs: number = 3000
  ): Promise<void> {

    this.atacanteNombre = atacanteNombre;
    this.atacanteColor = atacanteColor;
    this.atacantePais = atacantePais;

    this.defensorNombre = defensorNombre;
    this.defensorColor = defensorColor;
    this.defensorPais = defensorPais;



    this.dadosAtacante = dadosAtacante;
    this.dadosDefensor = dadosDefensor;
    this.audioService.playSonidoDado()

    this.mostrarDados = true;

    return new Promise<void>((resolve) => {
      setTimeout(() => {
        this.mostrarDados = false;
        resolve();
      }, duracionMs);
    });
  }





  private esperarConfirmacionJugador(): Promise<void> {
    return new Promise<void>((resolve) => {
      this.resolverConfirmacion = resolve;
    });
  }


  async ponerPaises() {
    for (const jugador of this.partida.jugadores) {

      for (const pais of jugador.paises) {

        this.mapaComponent.setearColor(jugador.color.color.toLowerCase(), pais.idPais);
        this.mapaComponent.cambiarFichas(pais.idPais,pais.fichas)
        await new Promise(resolve => setTimeout(resolve, 200));
      }
    }
  }




  async aplicarRefuerzo( turnoRefuerzoNuevo : TurnoRefuerzo){
    let largoNuevo=0
    let largoActual=0

    if( this.turnoRefuerzoActual.refuerzoModels != null){
      largoActual = this.turnoRefuerzoActual.refuerzoModels.length;
    }

    if(turnoRefuerzoNuevo.refuerzoModels!= null ){

      largoNuevo = turnoRefuerzoNuevo.refuerzoModels.length;
      if (largoNuevo > largoActual) {

        for (let indice = largoActual; indice < largoNuevo; indice++) {

          const juga = this.buscarJugador(turnoRefuerzoNuevo.idJugador);
          let refuerzo =turnoRefuerzoNuevo.refuerzoModels[indice];
          const pais = this.buscarPais(refuerzo.paisJugadorModel.idPais);

          if(juga){
            this.notiRef.agregar(`El jugador ${juga.nombre} reforzó ${pais?.nombre} con ${refuerzo.cantidad} fichas`, this.mapColorNombreToCss(juga.color?.color ?? 'white'));
          }

          this.mapaComponent.cambiarFichas(refuerzo.paisJugadorModel.idPais,refuerzo.cantidad)

          await new Promise(resolve => setTimeout(resolve, 300));
        }

      }
    }



  }

  async aplicarPrimerRefuerzo( turnoRefuerzoNuevo : TurnoRefuerzo){

    if(turnoRefuerzoNuevo.refuerzoModels!= null ){

      for (let indice = 0; indice < turnoRefuerzoNuevo.refuerzoModels.length; indice++) {
        const juga = this.buscarJugador(turnoRefuerzoNuevo.idJugador)

        let refuerzo =turnoRefuerzoNuevo.refuerzoModels[indice]

        const pais = this.buscarPais(refuerzo.paisJugadorModel.idPais)
        if(juga){
          this.notiRef.agregar(`El jugador ${juga.nombre} reforzó ${pais?.nombre ?? "pais"} con ${refuerzo.cantidad} fichas`, this.mapColorNombreToCss(juga.color?.color ?? 'white'));
        }


        this.mapaComponent.cambiarFichas(refuerzo.paisJugadorModel.idPais,refuerzo.cantidad)
        await new Promise(resolve => setTimeout(resolve, 300));

      }
    }
  }

  async aplicarAtaques( turnoAtaqueNuevo : TurnoAtaque){
    if( turnoAtaqueNuevo.acciones!= null && this.turnoAtaqueActual.acciones != null){
      let nuevo = turnoAtaqueNuevo.acciones.length
      let viejo = this.turnoAtaqueActual.acciones.length
      if(  nuevo > viejo ){
        for (let indice = viejo; indice < nuevo; indice++) {
          let accion =turnoAtaqueNuevo.acciones[indice]


          await this.aplicarAtaque(accion, turnoAtaqueNuevo.idJugador)

        }
      }
    }

  }

  async aplicarPrimerAtaque( turnoAtaqueNuevo : TurnoAtaque){
    if(turnoAtaqueNuevo.acciones!= null ){
      for (let indice = 0; indice < turnoAtaqueNuevo.acciones.length; indice++) {
        let accion =turnoAtaqueNuevo.acciones[indice]
        await this.aplicarAtaque(accion, turnoAtaqueNuevo.idJugador)

      }
    }
  }

  async aplicarAtaque(accion:Accion, idJugador:number){
    const jugador = this.buscarJugador(idJugador)
    if(accion.combate != null){


      const jugadorAtacante = this.buscarJugador(accion.combate.idJugadorAtaque)
      const paisAtk = this.buscarPais(accion.combate.idPaisAtk)
      const dadosAtk =  [];

      dadosAtk.push(accion.combate.atkDado1)

      if(accion.combate.atkDado2 > 0){
        dadosAtk.push(accion.combate.atkDado2)
      }
      if(accion.combate.atkDado3 > 0){
        dadosAtk.push(accion.combate.atkDado3)
      }




      const jugadorDefensor = this.buscarJugador(accion.combate.idJugadorDefensa)
      const paisDef = this.buscarPais(accion.combate.idPaisDef)
      const dadosDef =  [];

      dadosDef.push(accion.combate.defDado1)

      if(accion.combate.defDado2 > 0){
        dadosDef.push(accion.combate.defDado2)
      }
      if(accion.combate.defDado3 > 0){
        dadosDef.push(accion.combate.defDado3)
      }




      if(jugadorAtacante && jugadorDefensor && jugadorAtacante.color && jugadorDefensor.color && paisAtk && paisDef){
        if(jugadorAtacante.id != this.jugador.id){
          this.notiRef.agregar(`El jugador ${jugadorAtacante.nombre} atacó ${paisDef?.nombre ?? "pais"} con ${paisAtk?.nombre ?? "pais"}`, this.mapColorNombreToCss(jugadorAtacante.color?.color ?? 'white'));

        }

        await this.mostrarModalDados(
          jugadorAtacante.nombre, this.mapColorNombreToCss(jugadorAtacante.color.color), paisAtk.nombre ,
          jugadorDefensor.nombre, this.mapColorNombreToCss(jugadorDefensor.color.color), paisDef.nombre,
          dadosAtk,dadosDef
        );


        this.audioService.playSonidoCombate()
        this.mapaComponent.ataquePais(paisAtk.id, accion.combate.fichasAtk)
        this.mapaComponent.ataquePais(paisDef.id, accion.combate.fichasDef)
        this.mapaComponent.setearFichas(paisAtk.id, accion.combate.fichasAtk)
        this.mapaComponent.setearFichas(paisDef.id, accion.combate.fichasDef)



        if(accion.combate.fichasDef == 0 ){
          this.mapaComponent.perderPais(paisDef.id)
          await this.delay(2000);

        }

      }

    }
    if(accion.reagrupacion != null){
      if (jugador && jugador.color) {
        await this.tomarPais(accion.reagrupacion.idPaisorigen, accion.reagrupacion.idPaisdestino, jugador.color.color, accion.reagrupacion.cantidad)

      }
    }

  }


  async tomarPais(idOrigen:number, idDestino:number, color:string, cantidad:number){
    this.mapaComponent.setearColor(color.toLowerCase(), idDestino)
    await this.delay(200)
    this.reagrupar(idOrigen, idDestino, cantidad)
  }

  reagrupar(idOrigen:number, idDestino:number, cantidad:number){
    this.mapaComponent.cambiarFichas(idOrigen,(cantidad*(-1)))
    this.mapaComponent.cambiarFichas(idDestino,cantidad)
  }


  buscarJugador(id:number){
    for(const jugador of this.jugadores  ){
      if(jugador.id == id){
        return jugador;
      }

    }
    return null;

  }


  buscarPais(id:number){
    this.paises = this.mapaComponent.getPaises()
    for(const pais of this.paises  ){
      if(pais.id == id){
        return pais;
      }

    }
    return null;

  }

  private async ponerFichasContinente(idContinente:number , cantidad:number) {

    const paisesJugadorIds: number[] = this.buscarPaisContinente(idContinente);


    this.mapaComponent.habilitarPaises(paisesJugadorIds, cantidad);
  }

  buscarPaisContinente(id:number){
    this.paises = this.mapaComponent.getPaises()
    let paisesIds: number[] =[]

    for(const pais of this.paises  ){
      if(pais.continente.id == id){
        paisesIds.push(pais.id)

      }

    }
    return paisesIds;

  }

  private async ponerFichasTarjeta(mapa: Map<number, number> ) {
    const paisesJugadorIds: number[] = [];

    for (const pais of this.jugador.paises) {
      paisesJugadorIds.push(pais.idPais);
    }
    return paisesJugadorIds;

    //this.mapaComponent.habilitarPaises(paisesJugadorIds, cantidad);
  }




  avanzarFase() {
    this.audioService.playSonidoBoton()
    this.botonDeshabilitado = true;
    if(this.faseRonda == "REF"){
      if(this.verificarFichasAsignadas()){
        this.ponerRefuerzo()

      }
      else{
        this.botonDeshabilitado = false;
        //mensaje que faltan fichas por poner
      }

    }
    if(this.faseRonda =="ATK" || this.faseRonda == "REAG"){
      this.resolverConfirmacion();
    }
    else{
      this.resolverConfirmacion();
    }


  }





  ponerRefuerzo(){
    const asignaciones = this.mapaComponent.getAsignaciones();
    const refuerzos: RefuerzoDTO[] = [];

    for (const [idPaisStr, cantidad] of Object.entries(asignaciones)) {
      const idPais = Number(idPaisStr);

      refuerzos.push({
        idTurnoRef: this.turnoRefuerzoActual.id,
        idPais: idPais,
        cantidad: cantidad,
        tipoFicha: this.faseActual
      });
    }

    this.rondasService.enviarRefuerzos(refuerzos).subscribe(() => {
      this.resolverConfirmacion();
    });
  }








  private delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }







  verificarFichasAsignadas() {
    const total = this.mapaComponent.getTotalFichasAsignadas();
    const max = this.mapaComponent.getFichasMaximas();
    return total == max;
  }





  private async ponerFichasLibres(cantidad: number) {

    this.actualizarTextoBoton("Reforzar")
    const paisesJugadorIds: number[] = [];

    for (const pais of this.jugador.paises) {
      paisesJugadorIds.push(pais.idPais);
    }

    this.mapaComponent.habilitarPaises(paisesJugadorIds, cantidad);

    this.botonDeshabilitado = false; //crear metodo para cambiralo?

  }












  actualizarTextoBoton(texto:string) {
    this.textoBoton = `${texto}`;
  }


  paisDetalle?: PaisDetalle;


  actualizarInfoTurno(texto1: string, texto2: string, texto3: string, texto4: string) {
     this.infoTurno = {
      texto1: texto1,
      texto2: texto2,
      texto3: texto3,
      texto4: texto4,
    };
  }







  async onAtaque(datos: { idPaisDef: number; idPaisAtk: number }) {
    this.puedeAtacar = false;

    const ac: AccionCombatePostDTO = {
      idTurno: this.turnoAtaqueActual.id,
      idPaisAtk: datos.idPaisAtk,
      idPaisDef: datos.idPaisDef
    };


    try {
      const accion = await firstValueFrom(this.rondasService.hacerAtaque(ac));
      await this.aplicarAtaque(accion, this.jugador.id);

      if(accion.combate){
        if(accion.combate.fichasDef == 0){

          this.idPaisOrigen = accion.combate.idPaisAtk
          this.idPaisReagrupar= accion.combate.idPaisDef

          let maximo = accion.combate.fichasAtk - 1
          if(accion.combate.fichasAtk > 3)
          {
            maximo = 3
          }
          let color =this.mapColorNombreToCss(this.jugador.color?.color ?? 'white')
          this.titulo= "¿Con cuantas fichas desea tomar el pais?"
          this.botonTomarTexto= "Tomar pais"
          this.botonCancelarActivo = false
          this.min=1
          this.max= maximo
          this.colorFondo="#00000099"
          this.colorTexto="white"
          this.colorBotonTomar = color
          this.colorBotonCancelar = color
          this.colorControles = color

          this.mostrarModalReagrupar = true;



        }
        this.onPaisClickeado(datos.idPaisAtk)
      }

    } catch (error) {
      console.error('Error al hacer ataque:', error);
    }
  }

  async reagruparEvent(cantidad:number){


    this.idsReagrupados.push(this.idPaisOrigen)
    this.idsReagrupados.push(this.idPaisReagrupar)
    const ac: AccionReagrupacionPostDTO = {
      idTurno: this.turnoAtaqueActual.id,
      idPaisOrigen: this.idPaisOrigen,
      idPaisDestino: this.idPaisReagrupar,
      cantidad: cantidad
    };
    this.mostrarModalReagrupar = false;
    const accion = await firstValueFrom(this.rondasService.hacerReagrupacion(ac));
    await this.aplicarAtaque(accion, this.jugador.id);
    this.onPaisClickeado(this.idPaisOrigen)
  }


  async onReagrupar(datos: { idPaisDestino: number; idPaisOrigen: number }) {

    this.puedeReagrupar = false;
    this.idPaisOrigen = datos.idPaisOrigen
    this.idPaisReagrupar= datos.idPaisDestino
    this.paisesJugadoresService.obtenerDetallePais(this.partida.id, datos.idPaisOrigen)
      .subscribe(paisDetalle => {

        let maximo = paisDetalle.fichas - 1
        if(maximo > 3)
        {
          maximo = 3
        }

        let color =this.mapColorNombreToCss(this.jugador.color?.color ?? 'white')
        this.titulo= "¿Cuantas fichas quiere reagrupar?"
        this.botonTomarTexto= "Reagrupar"
        this.botonCancelarActivo = true
        this.min=1
        this.max= maximo
        this.colorFondo="#00000099"
        this.colorTexto="white"
        this.colorBotonTomar = color
        this.colorBotonCancelar = color
        this.colorControles = color

        this.mostrarModalReagrupar = true;



      });





  }









  onPaisClickeado(idPais: number) {
    console.log("por aca andamio")
    if(this.faseRonda == "REF"){
      const total = this.mapaComponent.getTotalFichasAsignadas();
      const max = this.mapaComponent.getFichasMaximas();
      let diferencia = max - total;
      switch (this.faseActual) {
        case 'DE_CONTINENTE':
          this.actualizarInfoTurno("Refuerzo:", "Continente", "Fichas Restantes:" ,diferencia.toString())
          break;
        case 'DE_PAISES':
          this.actualizarInfoTurno("Refuerzo:", "De paises", "Fichas Restantes:" ,diferencia.toString())
          break;
        case 'DE_CANJE':
          this.actualizarInfoTurno("Refuerzo:", "Canje", "Fichas Restantes:" ,diferencia.toString())
          break;
      }


      const idPartida = this.partida.id;
      this.paisesJugadoresService.obtenerDetallePais(idPartida, idPais)
        .subscribe(paisDetalle => {
          this.paisDetalle = paisDetalle;
          this.jugadorID = this.jugador.id;
          this.puedeAtacar = false;

        });

    }
    else{
      if(this.faseRonda == "ATK"){
        const idPartida = this.partida.id;
        this.puedeReagrupar = false;
        this.paisesJugadoresService.obtenerDetallePais(idPartida, idPais)
          .subscribe(paisDetalle => {
            this.paisDetalle = paisDetalle;

            if(this.paisDetalle.idDuenio == this.jugador.id && this.paisDetalle.fichas > 1){

              this.jugadorID = this.jugador.id;
              this.puedeAtacar = true;
            }
            else{

              this.jugadorID = this.jugador.id;
              this.puedeAtacar = false;

            }

          });
      }
      else{

        let puede = true;
        this.puedeAtacar = false;

        this.paisesJugadoresService.obtenerDetallePais(this.partida.id, idPais)
          .subscribe(paisDetalle => {
            this.paisDetalle = paisDetalle;
            if(this.paisDetalle.idDuenio == this.jugador.id && this.paisDetalle.fichas > 1){

              for (const ids of this.idsReagrupados) {

                if(ids == paisDetalle.idPais ){
                  puede = false;
                  break;
                }
              }

              if(puede){

                this.jugadorID = this.jugador.id;
                this.puedeReagrupar = true;
              }
              else{
                this.puedeReagrupar = false;
              }


            }
            else{
              this.puedeReagrupar = false;
            }




          });


      }

    }





  }


  mapColorNombreToCss(colorNombre: string): string {
    const mapa: Record<string, string> = {
      rojo: 'red',
      verde: 'green',
      azul: 'blue',
      amarillo: 'yellow',
      naranja: 'orange',
      negro: 'black',
      blanco: 'white',
      magenta: 'magenta',
      gris: 'gray',
      celeste: 'skyblue',
      rosa: 'pink'

    };
    return mapa[colorNombre.toLowerCase()] || 'gray';
  }



































}


