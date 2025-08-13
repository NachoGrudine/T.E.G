import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  standalone: true,
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  repeatPassword: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.username, this.password, this.repeatPassword).subscribe({
      next: (response) => {
        console.log('Registro exitoso:', response);
        // Alerta de éxito
        alert('¡Registro exitoso! Ahora puedes iniciar sesión');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Error en registro:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);

        // Manejar diferentes tipos de errores
        if (error.status === 409) {
          alert('El usuario ya existe. Intenta con otro nombre de usuario.');
        } else if (error.status === 400) {
          alert('Datos inválidos. Verifica que todos los campos estén completos.');
        } else if (error.status === 0) {
          alert('Error de conexión. Verifica tu conexión a internet.');
        } else if (error.status >= 500) {
          alert('Error del servidor. Intenta más tarde.');
        } else {
          alert('Error inesperado durante el registro. Intenta nuevamente.');
        }
      }
    });
  }
}

