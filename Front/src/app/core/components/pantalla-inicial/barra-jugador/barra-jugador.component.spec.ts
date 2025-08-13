import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarraJugadorComponent } from './barra-jugador.component';

describe('BarraJugadorComponent', () => {
  let component: BarraJugadorComponent;
  let fixture: ComponentFixture<BarraJugadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BarraJugadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BarraJugadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
