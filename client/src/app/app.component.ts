import { Component } from '@angular/core';
import {Router} from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

import {UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DVD Discs Exchange';

  constructor(private router: Router, private userService: UserService, private cdRef:ChangeDetectorRef){}

  ngAfterViewChecked() {
    // Avoid the error: ExpressionChangedAfterItHasBeenCheckedError: Expression has changed after it was checked
    this.cdRef.detectChanges();
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }

  get isUser() {
    return this.userService.isUser();
  }

  get userName(){
    return this.userService.userName;
  }
}
