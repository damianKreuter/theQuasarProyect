import { TestBed } from '@angular/core/testing';
import { NgbPaginationNumber } from '@ng-bootstrap/ng-bootstrap';

import { ServicioQuasarService } from './servicio-quasar.service';

describe('ServicioQuasarService', () => {
  let service: ServicioQuasarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicioQuasarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

});
