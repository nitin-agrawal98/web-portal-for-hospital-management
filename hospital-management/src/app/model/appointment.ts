import {Patient} from './patient';
import {Doctor} from './doctor';
import {Time} from '@angular/common';

export interface Appointment {
  id: number;
  patient: Patient;
  doctor: Doctor;
  date: Date;
  time: Time;
  healthProblem;
  prescription: string;
  feedback: string;
}
