import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-info-turno',
  templateUrl: './info-turno.component.html',
  styleUrls: ['./info-turno.component.css'],
  standalone: true
})
export class InfoTurnoComponent {
  @Input() texto1: string = '';
  @Input() texto2: string = '';
  @Input() texto3: string = '';
  @Input() texto4: string = '';
}
