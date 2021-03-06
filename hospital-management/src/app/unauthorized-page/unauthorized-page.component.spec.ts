import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UnauthorizedPageComponent} from './unauthorized-page.component';

describe('UnauthorizedComponent', () => {
  let component: UnauthorizedPageComponent;
  let fixture: ComponentFixture<UnauthorizedPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UnauthorizedPageComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UnauthorizedPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
