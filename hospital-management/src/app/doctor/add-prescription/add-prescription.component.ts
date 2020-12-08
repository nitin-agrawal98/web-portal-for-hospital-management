import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AppointmentService} from '../../services/appointment.service';

@Component({
  selector: 'app-add-prescription',
  templateUrl: './add-prescription.component.html',
  styleUrls: ['./add-prescription.component.css']
})
export class AddPrescriptionComponent implements OnInit {

  prescription;
  appointmentId;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: AppointmentService
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => this.appointmentId = params.id);
  }

  onSubmit(): void {
    this.service.addPrescription(this.prescription, this.appointmentId)
      .subscribe(
        response => {
          console.log('Success!', response);
          this.router.navigate(['../home'], {relativeTo: this.route.parent});
        },
        error => console.log('Error!', error)
      );
  }

}
