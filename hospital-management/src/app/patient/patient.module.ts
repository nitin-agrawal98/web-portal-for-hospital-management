import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {patientComponents, PatientRoutingModule} from './patient-routing.module';
import {SharedModule} from '../shared/shared.module';

@NgModule({
  declarations: [patientComponents],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PatientRoutingModule,
    SharedModule
  ]
})
export class PatientModule {
}
