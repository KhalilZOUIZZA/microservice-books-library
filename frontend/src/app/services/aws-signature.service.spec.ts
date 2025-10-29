import { TestBed } from '@angular/core/testing';

import { AwsSignatureService } from './aws-signature.service';

describe('AwsSignatureService', () => {
  let service: AwsSignatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AwsSignatureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
