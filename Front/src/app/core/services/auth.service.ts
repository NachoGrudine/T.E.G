import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, tap, catchError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8080/api'; // URL backend
  private authenticated = false;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {


    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    };

    return this.http.post(`${this.baseUrl}/login`,
      { username, password },
      httpOptions
    ).pipe(
      tap((response: any) => {
        console.log('Login response:', response);
        this.authenticated = true;
        localStorage.setItem('auth', 'true');

        if (response && response.username) {
          localStorage.setItem('user', response.username);
        }

        if (response && response.id) {
          localStorage.setItem('userId', response.id.toString());
        }
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('Auth Service Error:', error);
        throw error;
      })
    );
  }

  register(username: string, password: string, repeatPassword: string): Observable<any> {

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    };

    return this.http.post(`${this.baseUrl}/register`,
      { username, password, repeatPassword },
      httpOptions
    );
  }

  isAuthenticated(): boolean {
    return this.authenticated || localStorage.getItem('auth') === 'true';
  }





  logout() {
    this.authenticated = false;
    localStorage.removeItem('auth');
    localStorage.removeItem('user');
    localStorage.removeItem('userId');
  }
}
