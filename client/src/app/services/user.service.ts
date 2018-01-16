import {Injectable} from '@angular/core';

@Injectable()
export class UserService {
  isAdmin: boolean;
  userName: string;

  constructor() {
  }

  login(authResponse) {
    this.isAdmin = authResponse.authorities.some(el => el === 'ADMIN_USER');
    this.userName = authResponse.name;
  }

  logout() {
    this.userName = null;
    this.isAdmin = false;
  }

  isAdminUser(): boolean {
    return this.isAdmin;
  }

  isUser(): boolean {
    return this.userName && !this.isAdmin;
  }
}
