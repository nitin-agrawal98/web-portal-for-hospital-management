import {Component, OnInit} from '@angular/core';
import {AppointmentService} from '../../services/appointment.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-upcoming-appointments',
  templateUrl: './upcoming-appointments.component.html',
  styleUrls: ['./upcoming-appointments.component.css']
})
export class UpcomingAppointmentsComponent implements OnInit {

  isPatient;
  isDoctor;
  isReceptionist;
  appointments;
  pageArray;
  pageNumber = 0;
  recordsPerPage = 10;
  isFirstPage = true;
  isLastPage = false;
  filterBy = '';
  filterValue;
  healthProblem;

  constructor(
    private service: AppointmentService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    const role = sessionStorage.getItem('role');
    this.isPatient = role === 'ROLE_PATIENT';
    this.isDoctor = role === 'ROLE_DOCTOR';
    this.isReceptionist = role === 'ROLE_ADMIN';

    this.route.queryParams
      .subscribe(params => {
        this.pageNumber = params.pageNumber ? params.pageNumber : 0;
        this.recordsPerPage = params.pageSize ? params.pageSize : 10;
        this.filterBy = params.filterBy ? params.filterBy : 'patient';
        this.filterValue = params.filterValue ? params.filterValue : '';
        this.healthProblem = params.healthProblem ? params.healthProblem : '';
      });
    this.getAppointments();
  }

  getAppointments(): void {
    if (this.isPatient) {
      this.service.getUpcomingAppointmentsByPatientWithFilters(this.pageNumber, this.recordsPerPage, this.filterBy, this.filterValue, this.healthProblem)
        .subscribe(
          pageDetails => {
            if (pageDetails.content.length > 0) {
              this.handle(pageDetails);
            } else { this.isLastPage = true; }
          },
          error => {
            if (error.status === 403) {
              this.router.navigate(['/unauthorized']);
            }
            console.log('Error!', error);
          }
        );
    } else if (this.isDoctor) {
      this.service.getUpcomingAppointmentsByDoctorWithFilters(this.pageNumber, this.recordsPerPage, this.filterBy, this.filterValue, this.healthProblem)
        .subscribe(
          pageDetails => {
            if (pageDetails.content.length > 0) {
              this.handle(pageDetails);
            } else { this.isLastPage = true; }
          },
          error => {
            if (error.status === 403) {
              this.router.navigate(['/unauthorized']);
            }
            console.log('Error!', error);
          }
        );
    } else if (this.isReceptionist) {
      this.service.getUpcomingAppointmentsWithFilters(this.pageNumber, this.recordsPerPage, this.filterBy, this.filterValue, this.healthProblem)
        .subscribe(
          pageDetails => {
            if (pageDetails.content.length > 0) {
              this.handle(pageDetails);
            } else { this.isLastPage = true; }
          },
          error => {
            if (error.status === 403) {
              this.router.navigate(['/unauthorized']);
            }
            console.log('Error!', error);
          }
        );
    } else {
      this.router.navigate(['/login']);
    }
  }

  addQueryParams(): void {
    this.router.navigate(['.'], {
      queryParams: {
        pageNumber: this.pageNumber,
        pageSize: this.recordsPerPage,
        filterBy: this.filterBy,
        filterValue: this.filterValue,
        healthProblem: this.healthProblem
      },
      relativeTo: this.route
    });
  }

  reload(): void {
    location.reload();
  }

  handle(pageDetails): void {
    this.appointments = pageDetails.content;
    this.pageNumber = pageDetails.number;
    this.isFirstPage = this.pageNumber === 0;
    this.isLastPage = (this.pageNumber === pageDetails.totalPages - 1) || (pageDetails.totalPages === 0);
    this.recordsPerPage = pageDetails.size;

    this.pageArray = [];
    for (let i = 0; i < pageDetails.totalPages; i++) {
      this.pageArray.push(i);
    }
  }
}
