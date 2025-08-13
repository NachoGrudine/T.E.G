// src/app/services/estadisticas.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EstadisticasService {
  private apiUrl = 'http://localhost:8080/api/estadisticas';

  constructor(private http: HttpClient) {}

  partidasPorUsuario(idUsuario: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/partidas/${idUsuario}`);
  }

  porcentajeVictorias(idUsuario: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/porcentaje/${idUsuario}`);
  }

  coloresMasUsados(): Observable<any> {
    return this.http.get(`${this.apiUrl}/colores`);
  }

  objetivosCumplidos(): Observable<any> {
    return this.http.get(`${this.apiUrl}/objetivos`);
  }
}
