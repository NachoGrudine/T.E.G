import { Component, OnInit } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from './core/services/auth.service';
import { AudioService } from './core/services/audio.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  constructor(public authService: AuthService, private audioService: AudioService) {}

  ngOnInit(): void {

    document.addEventListener('click', () => {
      this.audioService.play();
    }, { once: true });
  }

  logout() {
    this.authService.logout();
  }
}
