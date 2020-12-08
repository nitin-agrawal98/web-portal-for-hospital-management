import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AppointmentService} from '../../services/appointment.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-book-next-appointment',
  templateUrl: './book-next-appointment.component.html',
  styleUrls: ['./book-next-appointment.component.css']
})
export class BookNextAppointmentComponent implements OnInit {

  appointmentForm: FormGroup;
  appointmentId;

  constructor(
    private fb: FormBuilder,
    private service: AppointmentService,
    private route: ActivatedRoute
  ) {
  }

  get date(): AbstractControl {
    return this.appointmentForm.get('date');
  }

  get time(): AbstractControl {
    return this.appointmentForm.get('time');
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => this.appointmentId = params.id);

    this.appointmentForm = this.fb.group({
      date: [null, Validators.required],
      time: [null, Validators.required]
    });
  }

  onSubmit(): void {
    this.service.bookNextAppointment(this.appointmentForm.value, this.appointmentId)
      .subscribe(
        response => console.log(response),
        error => console.log(error)
      );
  }

}
