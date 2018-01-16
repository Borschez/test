import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import { Disc } from '../disс';
import { MessageService } from './message.service';
import {UserService} from "./user.service";


@Injectable()
export class DiscService {

  private discUrl = 'http://localhost:8080/api/disc/';  // URL to web api
  private discsUrl = 'http://localhost:8080/api/discs/';  // URL to web api

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      //'X-Requested-With': 'XMLHttpRequest'
    })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private userService: UserService) { }

  canDelete(disc: Disc):boolean{
    return disc.owner && disc.owner.username == this.userService.userName;
  }

  canTake(disc: Disc): boolean{
    return disc.owner && disc.owner.username !== this.userService.userName && disc.discUser == null;
  }

  canGetBack(disc: Disc): boolean{
    return disc.discUser && disc.discUser.username == this.userService.userName;
  }

  takeDisc(id: number): Observable<Disc> {
    const url = `${this.discUrl}/${id}/take`;
    return this.http.get<Disc>(url, this.httpOptions).pipe(
      tap(_ => this.log(`fetched Disc id=${id}`)),
      catchError(this.handleError<Disc>(`getDisc id=${id}`))
    );
  }

  getBackDisc(id: number): Observable<Disc> {
    const url = `${this.discUrl}/${id}/get-back`;
    return this.http.get<Disc>(url, this.httpOptions).pipe(
      tap(_ => this.log(`fetched Disc id=${id}`)),
      catchError(this.handleError<Disc>(`getDisc id=${id}`))
    );
  }

  /** GET Discs from the server */
  getDiscs (): Observable<Disc[]> {

    return this.http.get<Disc[]>(this.discsUrl, this.httpOptions)
      .pipe(
        tap(discs => this.log(`fetched heroes`)),
        catchError(this.handleError('getDiscs', []))
      );
  }

  /** GET Discs from the server */
  getFilteredDiscs (filter:string): Observable<Disc[]> {
    var actual =  encodeURI(filter.replace('currentUserId',this.userService.userName).replace('currentUserId',this.userService.userName));
    const url =`${this.discsUrl}filter/${actual}/`;
    url
    return this.http.get<Disc[]>(url, this.httpOptions)
      .pipe(
        tap(discs => this.log(`fetched heroes`)),
        catchError(this.handleError('getDiscs', []))
      );
  }

  /** GET Disc by id. Will 404 if id not found */
  getDisc(id: number): Observable<Disc> {
    const url = `${this.discUrl}/${id}`;
    return this.http.get<Disc>(url, this.httpOptions).pipe(
      tap(_ => this.log(`fetched Disc id=${id}`)),
      catchError(this.handleError<Disc>(`getDisc id=${id}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new Disc to the server */
  addDisc (disc: Disc): Observable<Disc> {
    return this.http.post<Disc>(this.discUrl, disc, this.httpOptions).pipe(
      tap((disc: Disc) => this.log(`added hero w/ id=${disc.id}`)),
      catchError(this.handleError<Disc>('addDisc'))
    );
  }

  /** DELETE: delete the Disc from the server */
  deleteDisc (disc: Disc | number): Observable<Disc> {
    const id = typeof disc === 'number' ? disc : disc.id;
    const url = `${this.discUrl}/${id}`;

    return this.http.delete<Disc>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted Disc id=${id}`)),
      catchError(this.handleError<Disc>('deleteDisc'))
    );
  }

  /** PUT: update the Disc on the server */
  updateDisc (disc: Disc): Observable<any> {
      const id = typeof disc === 'number' ? disc : disc.id;
      const url = `${this.discUrl}/${id}`;
      return this.http.put(url, disc, this.httpOptions).pipe(
        tap(_ => this.log(`updated Disc id=${disc.id}`)),
        catchError(this.handleError<any>('updateHero'))
      );
  }


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add('HeroService: ' + message);
  }

}
