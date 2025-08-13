import { TestBed } from '@angular/core/testing';

import { PartidasService } from './partidas.service';

describe('PartidasServiceService', () => {
  let service: PartidasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartidasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
