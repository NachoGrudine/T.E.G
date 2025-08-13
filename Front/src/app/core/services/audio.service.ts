import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AudioService {
  private audio: HTMLAudioElement;

  constructor() {
    this.audio = new Audio('assets/fondoMusica.mp3');
    this.audio.loop = true;
    this.audio.volume = 0.1;
    this.audio.load();
  }

  play() {
    this.audio.play().catch(err => console.error('Autoplay blocked:', err));
  }

  pause() {
    this.audio.pause();
  }

  setVolume(vol: number) {
    this.audio.volume = vol;
  }

  playSonidoFicha() {
    const efecto = new Audio('assets/ponerFicha.mp3');
    efecto.volume = 1.0;
    efecto.play().catch(err => console.error('Error al reproducir efecto:', err));
  }

  playSonidoRefuerzo() {
    const efecto = new Audio('assets/comienzaRefuerzo.mp3');
    efecto.volume = 0.4;
    efecto.play().catch(err => console.error('Error al reproducir efecto:', err));
  }

  playSonidoDado() {
    const efecto = new Audio('assets/dadosss.mp3');
    efecto.volume = 1.0;
    efecto.play().catch(err => console.error('Error al reproducir efecto:', err));
  }

  playSonidoCombate() {
    const efecto = new Audio('assets/combatePrim.mp3');
    efecto.volume = 1.0;
    efecto.play().catch(err => console.error('Error al reproducir efecto:', err));
  }

  playSonidoBoton() {
    const efecto = new Audio('assets/botonPrim.mp3');
    efecto.volume = 1.0;
    efecto.play().catch(err => console.error('Error al reproducir efecto:', err));
  }
}
