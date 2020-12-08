import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Login} from '../model/login';
import {Observable} from 'rxjs';

@Injectable()
export class AuthService {

  baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  login(login: Login): Observable<any> {
    login.role = this.getRole();
    console.log(login);
    return this.http.post<any>(this.baseUrl + '/login', login, {responseType: 'text' as 'json'});
  }

  getRole(): string {
    return sessionStorage.getItem('role');
  }

}
