import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PetMainComponent } from './pet-main.component';

describe('PetMainComponent', () => {
  let component: PetMainComponent;
  let fixture: ComponentFixture<PetMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PetMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PetMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});