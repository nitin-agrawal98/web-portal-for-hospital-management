import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Doctor} from '../model/doctor';

@Injectable()
export class DoctorService {

  baseUrl = 'http://localhost:8080/doctors';

  constructor(private http: HttpClient) {
  }

  getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.baseUrl);
  }

  addDoctor(doctor: Doctor): Observable<any> {
    return this.http.post(this.baseUrl + '/register', doctor, {responseType: 'text' as 'json'});
  }

  getDoctorByUsername(): Observable<Doctor> {
    return this.http.get<Doctor>(this.baseUrl + '/name');
  }

  updateProfile(profileDetails): Observable<any> {
    return this.http.put(this.baseUrl + '/profile/update', profileDetails, {responseType: 'text' as 'json'});
  }
}
