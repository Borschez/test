import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {Disc} from "../disс";
import {DiscService} from "../services/disc.service";

@Component({
  selector: 'app-discs',
  templateUrl: './discs.component.html',
  styleUrls: ['./discs.component.css']
})
export class DiscsComponent implements OnInit {
  private filter: string;
  private title: string;

  discs: Disc[];

  constructor(private discService: DiscService,
              private route: ActivatedRoute ) { }

  ngOnInit() {
    this.route.data.subscribe(data=>{
      this.filter = data.filter;
      this.title = data.title;
    });
    this.getDiscs();
  }

  getDiscs(): void {
    if (this.filter){
      this.discService.getFilteredDiscs(this.filter)
        .subscribe(discs => this.discs = discs);
    }else {
      this.discService.getDiscs()
        .subscribe(discs => this.discs = discs);
    }
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.discService.addDisc({ name } as Disc)
      .subscribe(disc => {
        this.discs.push(disc);
      });
  }

  take(disc: Disc): void {
    this.discService.takeDisc(disc.id).subscribe(_ => this.getDiscs());
  }

  getBack(disc: Disc): void {
    this.discService.getBackDisc(disc.id).subscribe(_ => this.getDiscs());
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

  delete(disc: Disc): void {
    this.discs = this.discs.filter(h => h !== disc);
    this.discService.deleteDisc(disc).subscribe();
  }

}
