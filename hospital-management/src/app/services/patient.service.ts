import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Patient} from '../model/patient';

@Injectable()
export class PatientService {

  baseUrl = 'http://localhost:8080/patients';

  constructor(private http: HttpClient) {
  }

  getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.baseUrl);
  }

  addPatientRequest(patient: Patient): Observable<any> {
    return this.http.post(this.baseUrl + '/register', patient, {responseType: 'text'});
  }

  approvePatient(id: number): Observable<any> {
    return this.http.put(this.baseUrl + '/approve', id, {responseType: 'text'});
  }

  getPatientByUsername(): Observable<Patient> {
    return this.http.get<Patient>(this.baseUrl + '/name');
  }

  getPatientsByStatus(status: string): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.baseUrl + '/status/' + status);
  }

  cancelPatient(id: number): Observable<any> {
    return this.http.put(this.baseUrl + '/cancel', id, {responseType: 'text'});
  }

  updateProfile(patientProfile): Observable<any> {
    return this.http.put(this.baseUrl + '/profile/update', patientProfile, {responseType: 'text' as 'json'});
  }
}
