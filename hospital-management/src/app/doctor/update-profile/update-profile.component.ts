import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DoctorService} from '../../services/doctor.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  profileForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private service: DoctorService,
    private route: ActivatedRoute,
    private router: Router
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

  get qualifications(): FormArray {
    return this.profileForm.get('qualifications') as FormArray;
  }

  get specialization(): AbstractControl {
    return this.profileForm.get('specialization');
  }

  get loginName(): AbstractControl {
    return this.profileForm.get('loginName');
  }

  get password(): AbstractControl {
    return this.profileForm.get('passcode');
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
      qualifications: this.fb.array([
        this.fb.control(null, Validators.required)
      ]),
      specialization: [null, Validators.required],
      loginName: [{value: null, disabled: true}],
      passcode: [{value: null, disabled: true}]
    });
    this.getProfileAndFillForm();
  }

  addQualification(): void {
    this.qualifications.push(this.fb.control(null, Validators.required));
  }

  removeQualification(): void {
    if (this.qualifications.length > 1) {
      this.qualifications.removeAt(this.qualifications.length - 1);
    }
  }

  getProfileAndFillForm(): void {
    this.service.getDoctorByUsername()
      .subscribe(response => {
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
            qualifications: this.fb.array(response.qualifications),
            specialization: [response.specialization],
            loginName: [{value: response.loginName, disabled: true}],
            passcode: [{value: response.passcode, disabled: true}],
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
