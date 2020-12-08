import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DoctorService} from '../../services/doctor.service';

@Component({
  selector: 'app-doctor-register',
  templateUrl: './doctor-register.component.html',
  styleUrls: ['./doctor-register.component.css']
})
export class DoctorRegisterComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private doctorService: DoctorService,
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

  get qualifications(): FormArray {
    return this.registrationForm.get('qualifications') as FormArray;
  }

  get specialization(): AbstractControl {
    return this.registrationForm.get('specialization');
  }

  get loginName(): AbstractControl {
    return this.registrationForm.get('loginName');
  }

  get password(): AbstractControl {
    return this.registrationForm.get('passcode');
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
      qualifications: this.fb.array([
        this.fb.control(null, Validators.required)
      ]),
      specialization: [null, Validators.required],
      loginName: [null, Validators.required],
      passcode: [null, Validators.required]
    });
  }

  addQualification(): void {
    this.qualifications.push(this.fb.control(null, Validators.required));
  }

  removeQualification(): void {
    if (this.qualifications.length > 1) {
      this.qualifications.removeAt(this.qualifications.length - 1);
    }
  }

  onSubmit(): void {
    this.doctorService.addDoctor(this.registrationForm.value)
      .subscribe(
        response => {
          console.log('Success!', response);
          this.router.navigate(['/receptionist'], {relativeTo: this.route.parent});
        },
        error => console.error('Error!', error)
      );
  }
}
