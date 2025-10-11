import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ApiCardComponent } from './api-card.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('ApiCardComponent', () => {
  let component: ApiCardComponent;
  let fixture: ComponentFixture<ApiCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApiCardComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(ApiCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit api click event', () => {
    spyOn(component.apiClick, 'emit');
    component.makeRequest();
    expect(component.apiClick.emit).toHaveBeenCalledWith(component.apiText);
  });

  it('should have default values', () => {
    expect(component.title).toBeDefined();
    expect(component.subTitle).toBeDefined();
    expect(component.content).toBeDefined();
    expect(component.imgUrl).toBeDefined();
    expect(component.apiText).toBeDefined();
  });
});

