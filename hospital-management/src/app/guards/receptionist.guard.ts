import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable()
export class ReceptionistGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    const role = sessionStorage.getItem('role');
    const token = sessionStorage.getItem('token');

    if (token !== null && role === 'ROLE_ADMIN') {
      return true;
    } else if (token != null && role !== 'ROLE_ADMIN') {
      this.router.navigate(['/unauthorized']);
    } else {
      this.router.navigate(['/home']);
    }
    return false;
  }

}
