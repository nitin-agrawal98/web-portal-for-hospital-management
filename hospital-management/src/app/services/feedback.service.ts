import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class FeedbackService {

  baseUrl = 'http://localhost:8080/feedback';

  constructor(private http: HttpClient) {
  }

  addFeedback(id: number, feedback: string): Observable<any> {
    return this.http.post(this.baseUrl + '/add/appointment/' + id, feedback, {responseType: 'text' as 'json'});
  }

  viewFeedback(id: number): Observable<any> {
    return this.http.get(this.baseUrl + '/view/appointment/' + id, {responseType: 'text' as 'json'});
  }

  forwardFeedback(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + '/forward/appointment/' + id, {responseType: 'text' as 'json'});
  }
}
