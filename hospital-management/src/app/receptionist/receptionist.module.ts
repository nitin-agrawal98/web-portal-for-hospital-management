import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {receptionistComponents, ReceptionistRoutingModule} from './receptionist-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [receptionistComponents],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ReceptionistRoutingModule,
    SharedModule
  ]
})
export class ReceptionistModule {
}
