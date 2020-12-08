import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppointmentRequest} from '../model/appointment-request';
import {Appointment} from '../model/appointment';

@Injectable()
export class AppointmentRequestService {

  baseUrl = 'http://localhost:8080/requests';

  constructor(private http: HttpClient) {
  }

  getRequests(): Observable<AppointmentRequest[]> {
    return this.http.get<AppointmentRequest[]>(this.baseUrl);
  }

  addRequest(request: AppointmentRequest): Observable<any> {
    return this.http.post(this.baseUrl + '/book', request, {responseType: 'text' as 'json'});
  }

  getRequestById(id: number): Observable<AppointmentRequest> {
    return this.http.get<AppointmentRequest>(this.baseUrl + '/id/' + id);
  }

  getRequestsByPatient(): Observable<AppointmentRequest[]> {
    return this.http.get<AppointmentRequest[]>(this.baseUrl + '/patient');
  }

  approveRequest(appointment: Appointment, id: number): Observable<any> {
    return this.http.post(this.baseUrl + '/approve/' + id, appointment, {responseType: 'text' as 'json'});
  }

  delete(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + '/delete/' + id, {responseType: 'text' as 'json'});
  }
}
