import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pais } from '../models/pais';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class PaisesService {
  private API_URL = 'http://localhost:8080/api/paises';

  constructor(private http: HttpClient) {}

  getPaises(): Observable<Pais[]> {
    return this.http.get<Pais[]>(`${this.API_URL}`);
  }
}
