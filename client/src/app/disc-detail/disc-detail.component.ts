import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import {Disc} from "../disс";
import {DiscService} from "../services/disc.service";
import {Movie} from "../movie";
import {MovieService} from "../services/movie.service";

@Component({
  selector: 'app-Disc-detail',
  templateUrl: './disc-detail.component.html',
  styleUrls: ['./disc-detail.component.css']
})
export class DiscDetailComponent implements OnInit {
  @Input() disc: Disc;

  constructor(
    private route: ActivatedRoute,
    private discService: DiscService,
    private  movieService: MovieService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getDisc();
  }

  onSelectMovie(movie: Movie) {
    if (movie){
      this.disc.name = movie.title;
      this.disc.description = movie.overview;
      this.disc.posterURL = this.movieService.moviePosterURL+movie.poster_path;
    }
  }

  getDisc(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id == 0){
      this.disc = new Disc();
      this.disc.name ="";
      this.disc.description ="";
      this.disc.posterURL ="";
    }
    if (id > 0) {
      this.discService.getDisc(id)
        .subscribe(disc => this.disc = disc);
    }
  }

  canGetBack(disc: Disc): boolean{
    return this.discService.canGetBack(disc);
  }

  canTake(disc: Disc): boolean{
    return this.discService.canTake(disc);
  }

  canDelete(disc: Disc): boolean{
    return this.discService.canDelete(disc);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.disc.id) {
      this.discService.updateDisc(this.disc)
        .subscribe(() => this.goBack());
    }
    else{
      this.discService.addDisc(this.disc)
        .subscribe(() => this.goBack());
    }
  }

  changePosterURL(posterURL: string): void {

  }

  take(): void {
    if (this.disc.id)
      this.discService.takeDisc(this.disc.id)
        .subscribe(() => this.goBack());
  }
  getBack(): void {
    if (this.disc.id)
      this.discService.getBackDisc(this.disc.id)
        .subscribe(() => this.goBack());
  }


}
