import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FeedbackPrescriptionComponent} from './feedback-prescription.component';

describe('FeedbackPrescriptionComponent', () => {
  let component: FeedbackPrescriptionComponent;
  let fixture: ComponentFixture<FeedbackPrescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeedbackPrescriptionComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackPrescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
