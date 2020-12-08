import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(): boolean {

    const role = sessionStorage.getItem('role');
    const token = sessionStorage.getItem('token');

    if (role != null && token != null) {
      this.router.navigate(['/home']);
      return false;
    } else if (role != null) {
      return true;
    } else {
      this.router.navigate(['/home']);
      return false;
    }
  }

  private navigateToRespectiveHomePage(role): void {
    let url;
    switch (role) {
      case 'ROLE_PATIENT':
        url = 'patients';
        break;
      case 'ROLE_DOCTOR':
        url = 'doctors';
        break;
      default:
        url = 'receptionist';
    }
    this.router.navigate([url]);
  }
}
