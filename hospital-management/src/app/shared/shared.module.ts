import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UpcomingAppointmentsComponent} from './upcoming-appointments/upcoming-appointments.component';
import {RouterModule} from '@angular/router';
import {RequestsComponent} from './requests/requests.component';
import {MatTableModule} from '@angular/material/table';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {AppointmentsHistoryComponent} from './appointments-history/appointments-history.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {PatientDetailsComponent} from './patient-details/patient-details.component';
import {DoctorDetailsComponent} from './doctor-details/doctor-details.component';


@NgModule({
  declarations: [
    UpcomingAppointmentsComponent,
    RequestsComponent,
    AppointmentsHistoryComponent,
    PatientDetailsComponent,
    DoctorDetailsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatFormFieldModule,
    FormsModule,
    MatPaginatorModule
  ]
})
export class SharedModule {
}
