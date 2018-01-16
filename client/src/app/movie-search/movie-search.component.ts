import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subject }    from 'rxjs/Subject';
import { of }         from 'rxjs/observable/of';

import {Movie} from "../movie";

import { Observable } from 'rxjs/Observable';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';

import {MovieService} from "../services/movie.service";

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrls: ['./movie-search.component.css']
})
export class MovieSearchComponent implements OnInit {
  movies$: Observable<Movie[]>;
  private searchTerms = new Subject<string>();
  @Output() onSelected = new EventEmitter<Movie>();
  selectedMovie: Movie = null;

  constructor(private movieService: MovieService) { }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.movies$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.movieService.searchMovies(term)),
    );
  }

  select(movie): void{
    this.onSelected.emit(movie);
    this.selectedMovie = movie;
  }

}
