import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomePageComponent} from './home-page/home-page.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {LoginComponent} from './login/login.component';
import {AppComponent} from './app.component';
import {PatientGuard} from './guards/patient.guard';
import {DoctorGuard} from './guards/doctor.guard';
import {ReceptionistGuard} from './guards/receptionist.guard';
import {FeedbackPrescriptionComponent} from './feedback-prescription/feedback-prescription.component';
import {AboutComponent} from './about/about.component';
import {LoginGuard} from './guards/login.guard';
import {RegisterComponent} from './register/register.component';
import {UnauthorizedPageComponent} from './unauthorized-page/unauthorized-page.component';
import {HomePageGuard} from './guards/home-page.guard';
import {ServerErrorPageComponent} from './server-error-page/server-error-page.component';
import {PatientDetailsComponent} from './shared/patient-details/patient-details.component';
import {DoctorDetailsComponent} from './shared/doctor-details/doctor-details.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomePageComponent, canActivate: [HomePageGuard]},
  {path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
  {
    path: 'patients',
    loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule),
    canActivate: [PatientGuard]
  },
  {
    path: 'doctors',
    loadChildren: () => import('./doctor/doctor.module').then(m => m.DoctorModule),
    canActivate: [DoctorGuard]
  },
  {
    path: 'receptionist',
    loadChildren: () => import('./receptionist/receptionist.module').then(m => m.ReceptionistModule),
    canActivate: [ReceptionistGuard]
  },
  {path: 'register-patient', component: RegisterComponent},
  {path: 'feedback-prescription', component: FeedbackPrescriptionComponent},
  {path: 'patient-details', component: PatientDetailsComponent},
  {path: 'doctor-details', component: DoctorDetailsComponent},
  {path: 'about', component: AboutComponent},
  {path: 'unauthorized', component: UnauthorizedPageComponent},
  {path: 'server-error', component: ServerErrorPageComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const appComponents = [
  AppComponent,
  HomePageComponent,
  LoginComponent,
  RegisterComponent,
  PageNotFoundComponent,
  FeedbackPrescriptionComponent,
  AboutComponent,
  UnauthorizedPageComponent,
  ServerErrorPageComponent
];
