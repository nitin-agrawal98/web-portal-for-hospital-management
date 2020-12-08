import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {PatientService} from '../services/patient.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  get firstName(): AbstractControl {
    return this.registrationForm.get('firstName');
  }

  get lastName(): AbstractControl {
    return this.registrationForm.get('lastName');
  }

  get dob(): AbstractControl {
    return this.registrationForm.get('dob');
  }

  get city(): AbstractControl {
    return this.registrationForm.get('city');
  }

  get state(): AbstractControl {
    return this.registrationForm.get('state');
  }

  get country(): AbstractControl {
    return this.registrationForm.get('country');
  }

  get pinCode(): AbstractControl {
    return this.registrationForm.get('pinCode');
  }

  get contact(): AbstractControl {
    return this.registrationForm.get('contact');
  }

  get email(): AbstractControl {
    return this.registrationForm.get('email');
  }

  get loginName(): AbstractControl {
    return this.registrationForm.get('loginName');
  }

  get password(): AbstractControl {
    return this.registrationForm.get('passcode');
  }

  get question(): AbstractControl {
    return this.registrationForm.get('question');
  }

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      dob: [null, Validators.required],
      city: [null, Validators.required],
      state: [null, Validators.required],
      country: [null, Validators.required],
      pinCode: [null, Validators.required],
      contact: [null, [Validators.required, Validators.pattern('[0-9]{10}')]],
      email: [null, [Validators.required, Validators.email]],
      pastIllness: [null],
      loginName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(10)]],
      passcode: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(19)]],
      question: [null, Validators.required]
    });
  }

  onSubmit(): void {
    this.patientService.addPatientRequest(this.registrationForm.value)
      .subscribe(
        response => {
          console.log('Success!', response);
          this.router.navigate(['/login']);
        },
        error => console.error('Error!', error)
      );
  }
}
