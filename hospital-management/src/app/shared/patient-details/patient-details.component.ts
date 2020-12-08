import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AppointmentService} from '../../services/appointment.service';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.css']
})
export class PatientDetailsComponent implements OnInit {

  patient: any;
  isDoctor: boolean;

  constructor(
    private service: AppointmentService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.isDoctor = sessionStorage.getItem('role') === 'ROLE_DOCTOR';

    this.route.queryParams
      .subscribe(params => {
        this.service.getPatientDetails(params.id)
          .subscribe(
            response => this.patient = response,
            error => console.log(error)
          );
      });
  }

}
