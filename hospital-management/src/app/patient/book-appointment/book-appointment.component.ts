import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AppointmentRequestService} from '../../services/appointment-request.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit {

  requestForm: FormGroup;
  isBooked = false;

  constructor(
    private fb: FormBuilder,
    private requestService: AppointmentRequestService,
    private router: Router
  ) {
  }

  get date(): AbstractControl {
    return this.requestForm.get('date');
  }

  get time(): AbstractControl {
    return this.requestForm.get('time');
  }

  get healthProblem(): AbstractControl {
    return this.requestForm.get('healthProblem');
  }

  ngOnInit(): void {
    this.requestForm = this.fb.group({
      date: [null, Validators.required],
      time: [null, Validators.required],
      healthProblem: [null, Validators.required]
    });
  }

  onSubmit(): void {
    this.requestService.addRequest(this.requestForm.value)
      .subscribe(
        () => this.isBooked = true,
        error => {
          console.log('Error!', error);
          this.router.navigate(['/login']);
        }
      );
  }

}
