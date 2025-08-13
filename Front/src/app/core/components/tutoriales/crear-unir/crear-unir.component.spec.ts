import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearUnirComponent } from './crear-unir.component';

describe('CrearUnirComponent', () => {
  let component: CrearUnirComponent;
  let fixture: ComponentFixture<CrearUnirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearUnirComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearUnirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
