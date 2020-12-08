import {Component, OnInit} from '@angular/core';
import {Patient} from '../../model/patient';
import {PatientService} from '../../services/patient.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  status;
  inProgress;
  patients: Patient[];

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(
        params => this.setStatus(params.status)
      );
  }

  setStatus(status: string): void {
    this.status = status;
    this.inProgress = status === 'inProgress';
    this.patientService.getPatientsByStatus(status)
      .subscribe(
        response => {
          if (response.length > 0) {
            this.patients = response;
          } else {
            this.patients = null;
          }
        },
        error => {
          console.log('Error!', error);
          this.router.navigate(['/login']);
        }
      );
  }

  approve(id: number): void {
    this.patientService.approvePatient(id)
      .subscribe(
        response => {
          console.log(response);
          location.reload();
        },
        error => console.log('Error!', error)
      );
  }

  cancel(id: number): void {
    this.patientService.cancelPatient(id)
      .subscribe(
        response => {
          console.log(response);
          location.reload();
        },
        error => console.log(error)
      );
  }
}
