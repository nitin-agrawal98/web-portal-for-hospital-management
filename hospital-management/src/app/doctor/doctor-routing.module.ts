import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NavbarComponent} from './navbar/navbar.component';
import {UpcomingAppointmentsComponent} from '../shared/upcoming-appointments/upcoming-appointments.component';
import {BookNextAppointmentComponent} from './book-next-appointment/book-next-appointment.component';
import {AddPrescriptionComponent} from './add-prescription/add-prescription.component';
import {UpdateProfileComponent} from './update-profile/update-profile.component';
import {AppointmentsHistoryComponent} from '../shared/appointments-history/appointments-history.component';
import {FeedbackPrescriptionComponent} from '../feedback-prescription/feedback-prescription.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {
    path: 'home', component: NavbarComponent,
    children: [
      {path: '', redirectTo: 'appointments/upcoming', pathMatch: 'full'},
      {path: 'appointments/upcoming', component: UpcomingAppointmentsComponent},
      {path: 'appointments/history', component: AppointmentsHistoryComponent},
      {path: 'book-appointment', component: BookNextAppointmentComponent},
      {path: 'add-prescription', component: AddPrescriptionComponent},
      {path: 'update-profile', component: UpdateProfileComponent},
      {path: 'feedback-prescription', component: FeedbackPrescriptionComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule {
}

export const doctorComponents = [
  NavbarComponent,
  BookNextAppointmentComponent,
  AddPrescriptionComponent,
  UpdateProfileComponent
];
