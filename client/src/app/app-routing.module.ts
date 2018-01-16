import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DiscsComponent } from "./discs/discs.component";
import { DiscDetailComponent } from "./disc-detail/disc-detail.component";
import { AuthGuard} from "./services/auth-guard.service";

import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DiscsComponent, data : {filter : 'ownerName=currentUserId&discUserName=currentUserId', title:'My Discs'}, canActivate: [AuthGuard] },
  { path: 'add-disc', component: DiscDetailComponent, canActivate: [AuthGuard] },
  { path: 'disc/:id', component: DiscDetailComponent, canActivate: [AuthGuard] },
  { path: 'free-discs', component: DiscsComponent, data : {filter : 'ownerName=&discUserName=', title:'Free Discs'}, canActivate: [AuthGuard] },
  { path: 'using-discs', component: DiscsComponent, data : {filter : 'ownerName=&discUserName=currentUserId', title:'Using Discs'}, canActivate: [AuthGuard] },
  { path: 'given-discs', component: DiscsComponent, data : {filter : 'ownerName=currentUserId&discUserName=', title:'Given Discs'}, canActivate: [AuthGuard] },
  { path: 'discs', component: DiscsComponent, data : {filter : '', title:'All Discs'}, canActivate: [AuthGuard] },
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes) ]
})
export class AppRoutingModule {}
