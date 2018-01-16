import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';


@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback): Observable<Object>{
    var headers = credentials ? {
      authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
    } : {};
    return this.http.get('http://localhost:8080/auth', {headers: headers}).map(function(response) {
      callback && callback();
      return response;
    });
  }
}
