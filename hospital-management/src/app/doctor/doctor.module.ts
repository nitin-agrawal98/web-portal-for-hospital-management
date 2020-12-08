import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {doctorComponents, DoctorRoutingModule} from './doctor-routing.module';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [doctorComponents],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DoctorRoutingModule,
    SharedModule,
    FormsModule
  ]
})
export class DoctorModule {
}
