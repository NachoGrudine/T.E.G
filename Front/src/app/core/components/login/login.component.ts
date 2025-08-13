import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true,
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }
  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        // Alerta de éxito
        alert('¡Login exitoso! Bienvenido');
        this.router.navigate(['/principal']);
      },
      error: (error) => {
        console.error('Error completo:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);

        // Verificar específicamente el tipo de error
        if (error.status === 401 || error.status === 403) {
          alert('Usuario o contraseña incorrectos');
        } else if (error.status === 0) {
          alert('Error de conexión. Verifica tu conexión a internet.');
        } else if (error.status >= 500) {
          alert('Error del servidor. Intenta más tarde.');
        } else {
          alert('Error inesperado. Intenta nuevamente.');
        }
      }
    });
  }
}
