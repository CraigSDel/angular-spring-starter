import { TestBed } from '@angular/core/testing';
import { ConfigService } from './config.service';

describe('ConfigService', () => {
  let service: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ConfigService]
    });
    service = TestBed.inject(ConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return correct API URI', () => {
    const apiURI = service.getApiURI();
    expect(apiURI).toBeTruthy();
    expect(typeof apiURI).toBe('string');
  });

  it('should return localhost URI in development', () => {
    const apiURI = service.getApiURI();
    expect(apiURI).toContain('localhost');
  });
});

