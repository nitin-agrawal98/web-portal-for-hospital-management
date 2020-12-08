import {Component, OnInit} from '@angular/core';
import {AppointmentService} from '../../services/appointment.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css']
})
export class DoctorDetailsComponent implements OnInit {

  doctor: any;
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
        this.service.getDoctorDetails(params.id)
          .subscribe(
            response => this.doctor = response,
            error => console.log(error)
          );
      });
  }

}
