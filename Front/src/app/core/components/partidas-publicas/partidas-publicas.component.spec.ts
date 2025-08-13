import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartidasPublicasComponent } from './partidas-publicas.component';

describe('PartidasPublicasComponent', () => {
  let component: PartidasPublicasComponent;
  let fixture: ComponentFixture<PartidasPublicasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartidasPublicasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartidasPublicasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
