import {Component, AfterViewInit, Output, EventEmitter} from '@angular/core';
import { PaisesService } from '../../../services/paises.service';
import { Pais } from '../../../models/pais';
import {Partida} from '../../../models/partida';
import {NgIf} from '@angular/common';
import {AudioService} from '../../../services/audio.service';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',

  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements AfterViewInit {

  paises: Pais[] = [];
  private asignaciones: Record<number, number> = {};
  private fichasDisponibles = 0;
  private idsJugadores: number[] = [];
  private fichasMaximas = 0;
  private paisesActivos: number[] = [];

  private fichasMinimasPorPais = new Map<number, number>();
  private fichasExtrasPorPais = new Map<number, number>();


  constructor(private paisesService: PaisesService, private audioService: AudioService) {}

  ngAfterViewInit(): void {
    this.cargarPaises();

  }

  @Output() paisClickeado = new EventEmitter<number>();


  cargarPaises(): void {
    this.paisesService.getPaises().subscribe((data: Pais[]) => {
      this.paises = data;
      this.procesarPaises();
    });
  }

  getPaises() {
    return this.paises;
  }
  getTotalFichasAsignadas() {
    return Object.values(this.asignaciones).reduce((a, b) => a + b, 0);
  }

  getFichasMaximas() {
    return this.fichasMaximas;
  }

  private agregarEventosPais(elemento: HTMLElement, id: number): void {
    elemento.addEventListener("mouseenter", () => {
      elemento.parentElement?.appendChild(elemento);
    });

    elemento.addEventListener("click", (event: MouseEvent) => {
      event.preventDefault();
      this.paisClickeado.emit(id);
    });

    elemento.addEventListener("contextmenu", (event: MouseEvent) => {
      event.preventDefault();
    });
  }

  procesarPaises(): void {
    console.log("Paises cargados:", this.paises);

    this.paises.forEach((pais: Pais) => {
      const id = pais.id;

      const nombreElement = document.getElementById(`${id}_nombre`) ?? document.getElementById(`${id}-nombre`);
      const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
      const paisSVG = document.getElementById(String(id));

      if (nombreElement) {
        nombreElement.textContent = pais.nombre;
        nombreElement.classList.remove('nombre-pais');
        nombreElement.classList.remove('nombre_pais');
        nombreElement.classList.add('nombre-pais');
      } else {
        console.warn(`Nombre no encontrado para país ${id}`);
      }

      if (fichasElement) {
        fichasElement.textContent = "0";
        fichasElement.classList.remove('fichas-pais');
        fichasElement.classList.remove('fichas_pais');
        fichasElement.classList.add('fichas-pais');
      } else {
        console.warn(`Fichas no encontradas para país ${id}`);
      }

      if (paisSVG) {

        const paths = paisSVG.getElementsByTagName("path");

        Array.from(paths).forEach(path => {
          path.classList.forEach(cls => {
            if (cls.startsWith("pais-")) path.classList.remove(cls);
          });

          path.classList.add(`pais-fondo`);
        });

        const clon = paisSVG.cloneNode(true) as HTMLElement;
        clon.classList.add("grupo-pais-hover");
        paisSVG.replaceWith(clon);

        this.agregarEventosPais(clon, id);



      }
    });

    this.animarOlas();
  }








  cambiarFichas(id: number, delta: number): void {
    const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
    if (fichasElement) {
      let actual = parseInt(fichasElement.textContent ?? "0", 10);
      actual = Math.max(0, actual + delta);
      const nuevo = fichasElement.cloneNode(true) as HTMLElement;
      nuevo.textContent = actual.toString();
      fichasElement.replaceWith(nuevo);
      nuevo.classList.add("animar-cambio");

      const grupoPais = document.getElementById(String(id));
      if (grupoPais) {
        const nuevoGrupo = grupoPais.cloneNode(true) as HTMLElement;
        nuevoGrupo.classList.add("pais-animado");
        grupoPais.replaceWith(nuevoGrupo);

        this.agregarEventosPais(nuevoGrupo, id);

      }

    }
  }

  setearFichas(id: number, cantidad: number): void {
    const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
    if (fichasElement) {
      const nuevo = fichasElement.cloneNode(true) as HTMLElement;
      nuevo.textContent = cantidad.toString();
      fichasElement.replaceWith(nuevo);
      nuevo.classList.add("animar-cambio");


    }
  }

  setearColor(color: string, id: number): void {
    const grupoPais = document.getElementById(String(id));
    if (grupoPais) {

      const paths = grupoPais.getElementsByTagName("path");
      Array.from(paths).forEach(path => {
        path.classList.forEach(cls => {
          if (cls.startsWith("pais-")) path.classList.remove(cls);
        });
        path.classList.add(`pais-${color}`);
      });



      grupoPais.classList.remove("pais-animado");
      void grupoPais.offsetWidth;
      grupoPais.classList.add("pais-animado");

    }
    const grupoBandera = document.getElementById(`${id}-band`);
    if (grupoBandera) {
      const bandPaths = grupoBandera.getElementsByTagName("path");
      Array.from(bandPaths).forEach(path => {
        path.style.opacity = "0";
      });
    }
  }

  perderPais(id:number){
    const grupoPais = document.getElementById(String(id));
    if (grupoPais) {
      const paths = grupoPais.getElementsByTagName("path");
      Array.from(paths).forEach(path => {
        path.classList.forEach(cls => {
          if (cls.startsWith("pais-")) path.classList.remove(cls);
        });
        path.classList.add(`pais-fondo`);
      });
      grupoPais.classList.remove("pais-animado");
      void grupoPais.offsetWidth;
      grupoPais.classList.add("pais-animado");


    }
    const grupoBandera = document.getElementById(`${id}-band`);
    if (grupoBandera) {
      const bandPaths = grupoBandera.getElementsByTagName("path");
      Array.from(bandPaths).forEach(path => {
        path.style.opacity = "1";
      });
    }
  }




  ataquePais(id: number, fichas: number): void {
    const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
    const grupoPais = document.getElementById(String(id));

    if (grupoPais && fichasElement) {
      grupoPais.classList.add("pais-danio");
      let fichasActual = parseInt(fichasElement.textContent ?? "0", 10);
      let fichasPerdidas = fichasActual - fichas;

      const clon = fichasElement.cloneNode(true) as HTMLElement;
      clon.textContent = `-${Math.abs(fichasPerdidas)}`;
      clon.classList.add("fichas-danio");

      fichasElement.parentElement?.appendChild(clon);

      setTimeout(() => {
        grupoPais.classList.remove("pais-danio");
        clon.remove();
      }, 1200);
    }
  }



  private insertarRefuerzoVisual(id: number) {
    const fichasText = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
    if (!fichasText) return;
    let fichaExtra = document.getElementById(`${id}_extra`);
    if (!fichaExtra) {
      fichaExtra = document.createElement("span");
      fichaExtra.id = `${id}_extra`;
      fichaExtra.classList.add("extra-fichas");
      fichaExtra.textContent = "";
      fichasText.parentElement?.appendChild(fichaExtra);
    }
  }

  private actualizarRefuerzoVisual(id: number, valor: number) {

    this.audioService.playSonidoFicha();
    this.insertarRefuerzoVisual(id);
    const fichaExtra = document.getElementById(`${id}_extra`);
    if (!fichaExtra) return;
    fichaExtra.textContent = valor > 0 ? (`+${valor}`) : " ";

  }







  habilitarPaises(ids: number[], maxFichas: number) {
    this.deshabilitarTodos();
    this.fichasMaximas = maxFichas;
    this.paisesActivos = ids;

    this.fichasMinimasPorPais.clear();
    this.fichasExtrasPorPais.clear();

    this.paises.forEach(pais => {
      const element = document.getElementById(String(pais.id));
      if (!element) return;

      element.classList.remove('pais-activo', 'pais-inactivo');

      if (ids.includes(pais.id)) {
        element.classList.add('pais-activo');
        this.insertarRefuerzoVisual(pais.id);


        const fichasElement = document.getElementById(`${pais.id}_fichas`) ?? document.getElementById(`${pais.id}-fichas`);
        const minFichas = fichasElement ? parseInt(fichasElement.textContent ?? "0", 10) : 0;

        this.fichasMinimasPorPais.set(pais.id, minFichas);
        this.fichasExtrasPorPais.set(pais.id, 0);
        this.actualizarRefuerzoVisual(pais.id, 0);


        const newElement = element.cloneNode(true) as HTMLElement;
        element.parentNode?.replaceChild(newElement, element);

        newElement.addEventListener('click', (event: MouseEvent) => {
          event.preventDefault();
          if (event.button === 0) {
            this.cambiarFichasRefuerzo(pais.id, +1);
            this.paisClickeado.emit(pais.id);

          }
        });

        newElement.addEventListener('contextmenu', (event: MouseEvent) => {
          event.preventDefault();
          this.cambiarFichasRefuerzo(pais.id, -1);
          this.paisClickeado.emit(pais.id);

        });



      } else {
        element.classList.add('pais-inactivo');
      }
    });
  }

  cambiarFichasRefuerzo(id: number, delta: number): void {
    if (!this.paisesActivos.includes(id)) return;

    const minFichas = this.fichasMinimasPorPais.get(id) ?? 0;
    const extrasActuales = this.fichasExtrasPorPais.get(id) ?? 0;

    let extrasNuevos = extrasActuales + delta;


    if (extrasNuevos < 0) extrasNuevos = 0;


    let sumaExtras = 0;
    this.paisesActivos.forEach(pid => {
      sumaExtras += (pid === id) ? extrasNuevos : (this.fichasExtrasPorPais.get(pid) ?? 0);
    });
    if (sumaExtras > this.fichasMaximas) return;

    this.fichasExtrasPorPais.set(id, extrasNuevos);
    this.asignaciones[id] = extrasNuevos;

    const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
    if (!fichasElement) return;

    const nuevoValor = minFichas + extrasNuevos;

    const nuevo = fichasElement.cloneNode(true) as HTMLElement;
    nuevo.textContent = nuevoValor.toString();
    fichasElement.replaceWith(nuevo);


    nuevo.classList.add("animar-cambioRef");


    if (extrasNuevos > 0) {
      nuevo.classList.add("refuerzo");
      this.actualizarRefuerzoVisual(id, extrasNuevos);


    } else {
      nuevo.classList.remove("refuerzo");
      this.actualizarRefuerzoVisual(id, 0);
    }
  }


  deshabilitarTodos() {
    this.paises.forEach(pais => {
      const id = pais.id;
      const element = document.getElementById(String(id));
      const fichasElement = document.getElementById(`${id}_fichas`) ?? document.getElementById(`${id}-fichas`);
      const extra = this.asignaciones[id] || 0;
      if (element) {
        element.classList.remove('pais-activo', 'pais-inactivo');
      }
      if (fichasElement) {
        fichasElement.classList.remove("refuerzo");
      }

      if (extra > 0) {
        this.cambiarFichas(id, 0);
        this.actualizarRefuerzoVisual(id, 0);
        this.asignaciones[id] = 0;
      }
    });
    this.paisesActivos = [];
    this.fichasMaximas = 0;
  }

  setIdsJugadores(ids: number[]) {
    this.idsJugadores = ids;
  }


  getAsignaciones(){
    const asignacionesFiltradas: Record<number, number> = {};

    for (const [id, valor] of Object.entries(this.asignaciones)) {
      if (valor !== 0) {
        asignacionesFiltradas[Number(id)] = valor;
      }
    }
    return asignacionesFiltradas;
  }




//otrso metodos esteticos
  private animarOlas(): void {
    const olasGroup = document.getElementById("olas");
    if (!olasGroup) {
      console.warn("No se encontró el grupo con id 'olas'");
      return;
    }

    const paths = olasGroup.querySelectorAll("path");

    paths.forEach((path: SVGPathElement) => {
      const maxTranslateX = 7;
      const maxTranslateY = 7;
      const duration = 7000 + Math.random() * 5000;
      const directionX = Math.random() > 0.5 ? 1 : -1;

      let startTime: number | null = null;

      const animate = (time: number) => {
        if (!startTime) startTime = time;
        const elapsed = time - startTime;

        const moveX = Math.sin((elapsed / duration) * 2 * Math.PI) * maxTranslateX * directionX;
        const moveY = Math.cos((elapsed / (duration * 1.5)) * 2 * Math.PI) * maxTranslateY;

        path.setAttribute("transform", `translate(${moveX.toFixed(2)}, ${moveY.toFixed(2)})`);

        requestAnimationFrame(animate);
      };

      requestAnimationFrame(animate);
    });



  }







}
