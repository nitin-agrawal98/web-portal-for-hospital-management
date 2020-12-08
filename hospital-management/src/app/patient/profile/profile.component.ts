import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PatientService} from '../../services/patient.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profileForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: PatientService
  ) {
  }

  get firstName(): AbstractControl {
    return this.profileForm.get('firstName');
  }

  get lastName(): AbstractControl {
    return this.profileForm.get('lastName');
  }

  get dob(): AbstractControl {
    return this.profileForm.get('dob');
  }

  get city(): AbstractControl {
    return this.profileForm.get('city');
  }

  get state(): AbstractControl {
    return this.profileForm.get('state');
  }

  get country(): AbstractControl {
    return this.profileForm.get('country');
  }

  get pinCode(): AbstractControl {
    return this.profileForm.get('pinCode');
  }

  get contact(): AbstractControl {
    return this.profileForm.get('contact');
  }

  get email(): AbstractControl {
    return this.profileForm.get('email');
  }

  get loginName(): AbstractControl {
    return this.profileForm.get('loginName');
  }

  get password(): AbstractControl {
    return this.profileForm.get('passcode');
  }

  get question(): AbstractControl {
    return this.profileForm.get('question');
  }

  ngOnInit(): void {
    this.profileForm = this.fb.group({
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
      loginName: [{value: null, disabled: true}],
      passcode: [{value: null, disabled: true}],
      question: [null, Validators.required]
    });

    this.getProfileAndFillForm();
  }

  getProfileAndFillForm(): void {
    this.service.getPatientByUsername()
      .subscribe(
        response => {
          this.profileForm = this.fb.group({
            firstName: [response.firstName, Validators.required],
            lastName: [response.lastName, Validators.required],
            dob: [response.dob, Validators.required],
            city: [response.city, Validators.required],
            state: [response.state, Validators.required],
            country: [response.country, Validators.required],
            pinCode: [response.pinCode, Validators.required],
            contact: [response.contact, [Validators.required, Validators.pattern('[0-9]{10}')]],
            email: [response.email, [Validators.required, Validators.email]],
            pastIllness: [response.pastIllness],
            loginName: [{value: response.loginName, disabled: true}],
            passcode: [{value: response.passcode, disabled: true}],
            question: [response.question, Validators.required]
          });
        }
      );
  }

  onSubmit(): void {
    this.service.updateProfile(this.profileForm.value)
      .subscribe(
        response => {
          console.log('Success!', response);
        },
        error => console.error('Error!', error)
      );
  }
}
