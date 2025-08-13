import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensaje } from '../models/mensaje';


@Injectable({
  providedIn: 'root'
})
export class MensajesService {

  private apiUrl = 'http://localhost:8080/api/mensajes';

  constructor(private http: HttpClient) { }

  getAllMensajes(): Observable<Mensaje[]> {
    return this.http.get<Mensaje[]>(this.apiUrl);
  }
}
