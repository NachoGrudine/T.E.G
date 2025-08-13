import { Routes } from '@angular/router';
import {LandingPageComponent} from '../core/components/landing-page/landing-page.component';
import {LoginComponent} from '../core/components/login/login.component';
import {PrincipalComponent} from '../core/components/principal/principal.component';
import {TutorialesComponent} from '../core/components/tutoriales/tutoriales.component';
import {RegisterComponent} from '../core/components/register/register.component';
import {AuthGuard} from '../core/services/auth.guard';
import {CrearPartidaComponent} from '../core/components/crear-partida/crear-partida.component';
import {HistorialPartidas} from '../core/components/historial-partidas/historial-partidas';
import {MapaComponent} from '../core/components/pantalla-inicial/mapa/mapa.component';
import {PantallaInicialComponent} from '../core/components/pantalla-inicial/pantalla-inicial.component';
import {ContenedorLobbyComponent} from '../core/components/contenedor-lobby/contenedor-lobby.component';
import {EstadisticasComponent} from '../core/components/estadisticas/estadisticas.component';
import {ReglasComponent} from '../core/components/tutoriales/reglas/reglas.component';
import {CrearUnirComponent} from '../core/components/tutoriales/crear-unir/crear-unir.component';
import {ControlesComponent} from '../core/components/tutoriales/controles/controles.component';



export const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'principal', component: PrincipalComponent, canActivate: [AuthGuard]},
  { path: 'tutoriales', component: TutorialesComponent, canActivate: [AuthGuard]},
  { path: 'estadisticas', component: EstadisticasComponent, canActivate: [AuthGuard] },
  { path: 'crear-partida', component: CrearPartidaComponent },
  { path: 'historial-partidas', component: HistorialPartidas },
  { path: 'mapa', component: MapaComponent },
  { path: 'pantalla-inicial', component: PantallaInicialComponent },
  { path: 'lobby/:id', component: ContenedorLobbyComponent},
  { path: 'tutoriales/reglas', component: ReglasComponent },
  { path: 'tutoriales/crear-unir', component: CrearUnirComponent },
  { path: 'tutoriales/controles', component: ControlesComponent },




  //dejar al ultimo
  { path: '**', redirectTo: '' },


];
