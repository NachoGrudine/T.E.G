import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialPartidas } from './historial-partidas';

describe('PartidasComponent', () => {
  let component: HistorialPartidas;
  let fixture: ComponentFixture<HistorialPartidas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialPartidas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialPartidas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
