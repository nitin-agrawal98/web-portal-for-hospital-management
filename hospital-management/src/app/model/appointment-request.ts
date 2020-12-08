import {Patient} from './patient';
import {Time} from '@angular/common';

export interface AppointmentRequest {
  id: number;
  patient: Patient;
  date: Date;
  time: Time;
  healthProblem: string;
}
