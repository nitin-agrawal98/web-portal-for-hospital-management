import {Component, OnInit} from '@angular/core';
import {AppointmentService} from '../services/appointment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FeedbackService} from '../services/feedback.service';

@Component({
  selector: 'app-feedback-prescription',
  templateUrl: './feedback-prescription.component.html',
  styleUrls: ['./feedback-prescription.component.css']
})
export class FeedbackPrescriptionComponent implements OnInit {

  isPatient;
  isDoctor;
  isReceptionist;
  feedback;
  prescription;
  appointmentId;
  patientFeedback;
  isForwardedFeedback;

  constructor(
    private appointmentService: AppointmentService,
    private router: Router,
    private route: ActivatedRoute,
    private feedbackService: FeedbackService
  ) {
  }

  ngOnInit(): void {
    const role = sessionStorage.getItem('role');
    this.isPatient = role === 'ROLE_PATIENT';
    this.isDoctor = role === 'ROLE_DOCTOR';
    this.isReceptionist = role === 'ROLE_ADMIN';

    this.route.queryParams
      .subscribe(params => this.appointmentId = params.id);

    this.getAppointment();
  }


  getAppointment(): void {
    if (this.isPatient) {
      this.appointmentService.getAppointmentByPatientAndId(this.appointmentId)
        .subscribe(
          response => {
            this.prescription = response.prescription;
          },
          error => console.log(error)
        );
    } else if (this.isDoctor) {
      this.appointmentService.getAppointmentByDoctorAndId(this.appointmentId)
        .subscribe(
          response => {
            this.feedback = response.feedback;
            this.prescription = response.prescription;
          },
          error => console.log(error)
        );
    } else if (this.isReceptionist) {
      this.feedbackService.viewFeedback(this.appointmentId)
        .subscribe(
          response => this.feedback = this.formatted(response),
          error => console.log(error)
        );
      this.appointmentService.getAppointmentById(this.appointmentId)
        .subscribe(
          response => this.prescription = response.prescription,
          error => console.log(error)
        );
    }
  }

  formatted(feedback: string): string {
    if (feedback.startsWith('Forwarded')) {
      this.isForwardedFeedback = true;
      return feedback.substring(9);
    } else if (feedback.startsWith('Not Forwarded')) {
      this.isForwardedFeedback = false;
      return feedback.substring(13);
    }
  }

  onSubmit(): void {
    this.feedbackService.addFeedback(this.appointmentId, this.patientFeedback)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['/patients']);
        },
        error => console.log(error)
      );
  }

  forward(): void {
    this.feedbackService.forwardFeedback(this.appointmentId)
      .subscribe(
        response => console.log(response),
        error => console.log(error)
      );
  }
}
