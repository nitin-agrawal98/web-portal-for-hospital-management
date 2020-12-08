import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let tokenizedReq = req.clone();
    const token = sessionStorage.getItem('token');
    if (token !== null) {
      tokenizedReq = req.clone({
        setHeaders: {Authorization: token}
      });
    }
    return next.handle(tokenizedReq);
  }
}
