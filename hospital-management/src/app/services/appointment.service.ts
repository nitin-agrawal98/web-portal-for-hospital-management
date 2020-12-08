import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Appointment} from '../model/appointment';
import {filter} from 'rxjs/operators';

@Injectable()
export class AppointmentService {

  baseUrl = 'http://localhost:8080/appointments';

  constructor(private http: HttpClient) {
  }

  getUpcomingAppointmentsWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/upcoming', {params});
  }

  getAppointmentsHistory(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.baseUrl + '/history');
  }

  bookNextAppointment(appointmentDetails, id: number): Observable<any> {
    return this.http.post(this.baseUrl + '/book/old/' + id, appointmentDetails, {responseType: 'text' as 'json'});
  }

  getAppointmentById(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(this.baseUrl + '/id/' + id);
  }

  getAppointmentByPatientAndId(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(this.baseUrl + '/patient/appointment/' + id);
  }

  getAppointmentByDoctorAndId(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(this.baseUrl + '/doctor/appointment/' + id);
  }

  getUpcomingAppointmentsByPatientWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/patient/upcoming', {params});
  }

  getUpcomingAppointmentsByDoctorWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/doctor/upcoming', {params});
  }

  getAppointmentsHistoryByPatient(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.baseUrl + '/patient/history');
  }

  getAppointmentsHistoryByDoctor(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.baseUrl + '/doctor/history');
  }


  getAppointmentsByDoctorAndPatient(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.baseUrl + '/doctor/patient/' + patientId);
  }

  addPrescription(prescription: string, appointmentId: number): Observable<any> {
    return this.http.put(this.baseUrl + '/add-prescription/id/' + appointmentId, prescription, {responseType: 'text' as 'json'});
  }

  getPatientDetails(id: number): Observable<any> {
    return this.http.get(this.baseUrl + '/patient-details/' + id);
  }

  getDoctorDetails(id: number): Observable<any> {
    return this.http.get(this.baseUrl + '/doctor-details/' + id);
  }

  getHistoryAppointmentsWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/history', {params});
  }

  getHistoryAppointmentsByPatientWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/patient/history', {params});
  }

  getHistoryAppointmentsByDoctorWithFilters(pageNumber, pageSize, filterBy, filterValue, healthProblem): Observable<any> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize)
      .set('filterBy', filterBy)
      .set('filterValue', filterValue)
      .set('healthProblem', healthProblem);

    return this.http.get(this.baseUrl + '/doctor/history', {params});
  }
}
