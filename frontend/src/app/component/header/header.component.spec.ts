import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HeaderComponent } from './header.component';
import { AuthService } from '../../service/auth.service';
import { UserService } from '../../service/user.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let authService: jasmine.SpyObj<AuthService>;
  let userService: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['hasToken']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getMyInfo']);

    await TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [RouterTestingModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: UserService, useValue: userServiceSpy }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check if user has signed in', () => {
    authService.hasToken.and.returnValue(true);
    expect(component.hasSignedIn()).toBe(true);
  });

  it('should get username when signed in', () => {
    const mockUser = { username: 'testuser' };
    authService.hasToken.and.returnValue(true);
    userService.getMyInfo.and.returnValue(of(mockUser));
    
    component.ngOnInit();
    expect(component.userName()).toBe('testuser');
  });
});

