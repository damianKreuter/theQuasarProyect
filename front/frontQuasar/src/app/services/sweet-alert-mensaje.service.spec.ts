import { TestBed } from '@angular/core/testing';

import { SweetAlertMensajeService } from './sweet-alert-mensaje.service';

describe('SweetAlertMensajeService', () => {
  let service: SweetAlertMensajeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SweetAlertMensajeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
