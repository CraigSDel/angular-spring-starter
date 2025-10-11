import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    localStorage.clear();
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set access token', () => {
    const token = 'test-token';
    service.setAccessToken(token);
    expect(localStorage.getItem('jwt')).toBe(token);
  });

  it('should get access token', () => {
    const token = 'test-token';
    localStorage.setItem('jwt', token);
    expect(service.getAccessToken()).toBe(token);
  });

  it('should return null when no token exists', () => {
    expect(service.getAccessToken()).toBeNull();
  });

  it('should remove access token', () => {
    const token = 'test-token';
    localStorage.setItem('jwt', token);
    service.removeAccessToken();
    expect(localStorage.getItem('jwt')).toBeNull();
  });

  it('should return true when user has token', () => {
    const token = 'test-token';
    localStorage.setItem('jwt', token);
    expect(service.hasToken()).toBe(true);
  });

  it('should return false when user has no token', () => {
    expect(service.hasToken()).toBe(false);
  });
});

