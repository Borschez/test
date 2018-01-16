import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

import {AuthenticationService} from "../services/authentication.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  error = '';
  redirectUrl: string;
  credentials : any = {};

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private userService: UserService) {
    this.redirectUrl = this.activatedRoute.snapshot.queryParams['redirectTo'];
  }

  ngOnInit(): void {
    this.userService.logout();
  }

  login() {
    this.redirectUrl = this.activatedRoute.snapshot.queryParams['redirectTo'];
    this.loading = true;

    this.credentials = {username:this.model.username, password:this.model.password};

    this.authenticationService.authenticate(this.credentials, this.navigateAfterSuccess())
      .subscribe(response =>{ this.userService.login(response); this.navigateAfterSuccess() });
    return false;
  }

  private navigateAfterSuccess() {
    if (this.redirectUrl) {
      this.router.navigateByUrl(this.redirectUrl);
    } else {
      this.router.navigate(['/']);
    }
  }
}
