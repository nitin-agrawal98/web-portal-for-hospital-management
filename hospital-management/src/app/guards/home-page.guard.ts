import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class HomePageGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    const role = sessionStorage.getItem('role');
    const token = sessionStorage.getItem('token');

    if (role != null && token != null) {
      this.navigateToRespectiveHomePage(role);
      return false;
    } else if (role != null) {
      this.router.navigate(['/login']);
      return false;
    } else {
      return true;
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
