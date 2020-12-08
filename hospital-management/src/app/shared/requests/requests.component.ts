import {Component, OnInit} from '@angular/core';
import {AppointmentRequestService} from '../../services/appointment-request.service';
import {AppointmentRequest} from '../../model/appointment-request';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  isPatient;
  isReceptionist;
  requests;
  ifPatientColumns: string[] = ['serial', 'patient', 'date', 'time', 'healthProblem'];
  ifReceptionistColumns: string[] = ['serial', 'patient', 'date', 'time', 'healthProblem', 'approve'];

  constructor(
    private service: AppointmentRequestService
  ) {
  }

  ngOnInit(): void {
    const role = sessionStorage.getItem('role');

    this.isPatient = role === 'ROLE_PATIENT';
    this.isReceptionist = role === 'ROLE_ADMIN';

    this.getRequests();
  }

  getRequests(): void {
    if (this.isPatient) {
      this.service.getRequestsByPatient()
        .subscribe(
          response => {
            if (response.length > 0) {
              this.requests = new MatTableDataSource<AppointmentRequest>(response);
            }
          },
          error => console.log(error)
        );
    } else if (this.isReceptionist) {
      this.service.getRequests()
        .subscribe(
          response => {
            if (response.length > 0) {
              this.requests = new MatTableDataSource<AppointmentRequest>(response);
            }
          },
          error => console.log(error)
        );
    } else {
      console.log('Not authorized');
    }
  }

  getDisplayedColumns(): string[] {
    return this.isReceptionist ? this.ifReceptionistColumns : this.ifPatientColumns;
  }
}
