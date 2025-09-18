import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private user: { nom: string; email: string } | null = null;

  setUser(user: { nom: string; email: string }) {
    this.user = user;
    localStorage.setItem('user', JSON.stringify(this.user));
  }

  getUser() {
    if (!this.user) {
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        this.user = JSON.parse(storedUser);
      }
    }
    return this.user;
  }

  logout() {
    this.user = null;
    localStorage.removeItem('user');
  }
}
