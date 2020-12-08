import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BookNextAppointmentComponent} from './book-next-appointment.component';

describe('BookNextAppointmentComponent', () => {
  let component: BookNextAppointmentComponent;
  let fixture: ComponentFixture<BookNextAppointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookNextAppointmentComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookNextAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
