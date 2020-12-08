import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalidAuth = false;
  isPatient = false;
  role;

  constructor(
    private fb: FormBuilder,
    private service: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.role = sessionStorage.getItem('role');
    this.isPatient = this.role === 'ROLE_PATIENT';
    this.loginForm = this.fb.group({
      username: [null],
      password: [null]
    });
  }

  onSubmit(): void {
    this.service.login(this.loginForm.value)
      .subscribe(
        response => {
          sessionStorage.setItem('token', 'Bearer ' + response);
          this.invalidAuth = false;
          this.router.navigate(['/home']);
        },
        error => {
          if (error.status === 403 || error.status === 401) {
            this.invalidAuth = true;
          }
        }
      );
  }

  logout(): void {
    sessionStorage.removeItem('role');
    this.router.navigate(['/home']);
  }
}
