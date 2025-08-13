import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {PaisDetalle} from '../models/paisDetalle';

@Injectable({
  providedIn: 'root'
})
export class PaisesJugadoresService {

  private apiUrl = 'http://localhost:8080/api/paisesJugadores'; //

  constructor(private http: HttpClient) { }

  obtenerDetallePais(idPartida: number, idPais: number): Observable<PaisDetalle> {
    const params = new HttpParams()
      .set('idPartida', idPartida.toString())
      .set('idPais', idPais.toString());

    return this.http.get<PaisDetalle>(`${this.apiUrl}`, { params });
  }
}
