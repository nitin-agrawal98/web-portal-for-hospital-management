import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {appComponents, AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PatientGuard} from './guards/patient.guard';
import {DoctorGuard} from './guards/doctor.guard';
import {ReceptionistGuard} from './guards/receptionist.guard';
import {ServicesModule} from './services/services.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';

@NgModule({
  declarations: [appComponents],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ServicesModule,
    MatTableModule,
    BrowserAnimationsModule
  ],
  providers: [
    PatientGuard,
    DoctorGuard,
    ReceptionistGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
