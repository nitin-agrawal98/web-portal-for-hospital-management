import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Doctor} from '../../model/doctor';
import {AppointmentRequestService} from '../../services/appointment-request.service';
import {Patient} from '../../model/patient';
import {DoctorService} from '../../services/doctor.service';

@Component({
  selector: 'app-assign-doctor',
  templateUrl: './assign-doctor.component.html',
  styleUrls: ['./assign-doctor.component.css']
})
export class AssignDoctorComponent implements OnInit {

  form: FormGroup;
  requestId;
  appointment;
  doctors: Doctor[];
  assignedDoctor: Doctor;
  patient: Patient;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private requestService: AppointmentRequestService,
    private doctorService: DoctorService
  ) {
  }

  get date(): AbstractControl {
    return this.form.get('date');
  }

  get time(): AbstractControl {
    return this.form.get('time');
  }

  get doctor(): AbstractControl {
    return this.form.get('doctor');
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => this.requestId = params.id);

    this.form = this.fb.group({
      patient: [],
      date: [],
      time: [],
      healthProblem: [],
      doctor: []
    });

    this.getDoctors();
    this.getRequestAndFillForm();

  }

  getDoctors(): void {
    this.doctorService.getDoctors()
      .subscribe(
        response => {
          this.doctors = response;
        },
        error => console.log(error)
      );
  }

  getRequestAndFillForm(): void {
    this.requestService.getRequestById(this.requestId)
      .subscribe(
        response => {
          this.patient = response.patient;

          this.form = this.fb.group({
            patient: [response.patient.firstName + ' ' + response.patient.lastName],
            date: [response.date, Validators.required],
            time: [response.time, Validators.required],
            healthProblem: [response.healthProblem],
            doctor: [null, Validators.required]
          });
        },
        error => console.log(error)
      );
  }

  onApprove(): void {
    this.appointment = {
      patient: this.patient,
      doctor: this.assignedDoctor,
      date: this.form.value.date,
      time: this.form.value.time,
      healthProblem: this.form.value.healthProblem,
    };
    this.requestService.approveRequest(this.appointment, this.requestId)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['requests'], {relativeTo: this.route.parent});
        },
        error => console.log(error)
      );
  }

  onCancel(): void {
    this.requestService.delete(this.requestId)
      .subscribe(
        response => {
          console.log(response);
          this.router.navigate(['receptionist'], {relativeTo: this.route.root});
        },
        error => console.log(error)
      );
  }
}
