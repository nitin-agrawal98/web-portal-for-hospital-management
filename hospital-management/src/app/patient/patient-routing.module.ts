import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BookAppointmentComponent} from './book-appointment/book-appointment.component';
import {NavbarComponent} from './navbar/navbar.component';
import {UpcomingAppointmentsComponent} from '../shared/upcoming-appointments/upcoming-appointments.component';
import {RequestsComponent} from '../shared/requests/requests.component';
import {AppointmentsHistoryComponent} from '../shared/appointments-history/appointments-history.component';
import {ProfileComponent} from './profile/profile.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {
    path: 'home', component: NavbarComponent,
    children: [
      {path: '', redirectTo: 'appointments/upcoming', pathMatch: 'full'},
      {path: 'appointments/upcoming', component: UpcomingAppointmentsComponent},
      {path: 'appointments/history', component: AppointmentsHistoryComponent},
      {path: 'pending-appointments', component: RequestsComponent},
      {path: 'book-appointment', component: BookAppointmentComponent},
      {path: 'profile', component: ProfileComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule {
}

export const patientComponents = [
  NavbarComponent,
  BookAppointmentComponent,
  ProfileComponent
];
