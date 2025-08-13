import { Component } from '@angular/core';
import {MapaComponent} from "../pantalla-inicial/mapa/mapa.component";

@Component({
  selector: 'app-landing-page',
    imports: [
        MapaComponent
    ],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}
