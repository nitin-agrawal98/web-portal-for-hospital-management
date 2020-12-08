import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PatientService} from './patient.service';
import {DoctorService} from './doctor.service';
import {AppointmentService} from './appointment.service';
import {AppointmentRequestService} from './appointment-request.service';
import {FeedbackService} from './feedback.service';
import {TokenInterceptorService} from './token-interceptor.service';
import {AuthService} from './auth.service';
import {HTTP_INTERCEPTORS} from '@angular/common/http';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
  ],
  providers: [
    PatientService,
    DoctorService,
    AppointmentService,
    AppointmentRequestService,
    FeedbackService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ]
})
export class ServicesModule {
}
