import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule }    from '@angular/forms';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { DiscDetailComponent } from './disc-detail/disc-detail.component';
import { AppRoutingModule } from './app-routing.module';
import { DiscsComponent } from './discs/discs.component';
import { DiscService} from "./services/disc.service";
import { MessageService} from "./services/message.service";
import { LoginComponent } from './login/login.component';
import {UserService} from './services/user.service';
import {AuthenticationService} from './services/authentication.service';
import { AuthGuard} from "./services/auth-guard.service";
import {AuthInterceptor} from "./auth.interceptor";
import { MovieSearchComponent } from './movie-search/movie-search.component';
import {MovieService} from "./services/movie.service";

@NgModule({
  declarations: [
    AppComponent,
    DiscDetailComponent,
    DiscsComponent,
    LoginComponent,
    MovieSearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    NgbModule.forRoot(),
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [ DiscService, MessageService,
    {
      provide : HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi   : true,
    },
    AuthenticationService,
    AuthGuard,
    UserService,
    MovieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
