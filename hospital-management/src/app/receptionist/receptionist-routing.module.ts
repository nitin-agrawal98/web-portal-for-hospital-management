import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NavbarComponent} from './navbar/navbar.component';
import {PatientsComponent} from './patients/patients.component';
import {DoctorRegisterComponent} from './doctor-register/doctor-register.component';
import {AssignDoctorComponent} from './assign-doctor/assign-doctor.component';
import {UpcomingAppointmentsComponent} from '../shared/upcoming-appointments/upcoming-appointments.component';
import {RequestsComponent} from '../shared/requests/requests.component';
import {AppointmentsHistoryComponent} from '../shared/appointments-history/appointments-history.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {
    path: 'home', component: NavbarComponent,
    children: [
      {path: '', redirectTo: 'appointments/upcoming', pathMatch: 'full'},
      {path: 'patients', component: PatientsComponent},
      {path: 'requests', component: RequestsComponent},
      {path: 'doctors', component: DoctorRegisterComponent},
      {path: 'appointments/upcoming', component: UpcomingAppointmentsComponent},
      {path: 'appointments/history', component: AppointmentsHistoryComponent},
      {path: 'approve-cancel/request', component: AssignDoctorComponent}]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReceptionistRoutingModule {
}

export const receptionistComponents = [
  NavbarComponent,
  PatientsComponent,
  DoctorRegisterComponent,
  AssignDoctorComponent
];
