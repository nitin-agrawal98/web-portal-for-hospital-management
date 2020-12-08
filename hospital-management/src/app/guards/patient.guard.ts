import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable()
export class PatientGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    const role = sessionStorage.getItem('role');
    const token = sessionStorage.getItem('token');

    if (token !== null && role === 'ROLE_PATIENT') {
      return true;
    } else if (token != null && role !== 'ROLE_PATIENT') {
      this.router.navigate(['/unauthorized']);
    } else {
      this.router.navigate(['/home']);
    }
    return false;
  }

}
