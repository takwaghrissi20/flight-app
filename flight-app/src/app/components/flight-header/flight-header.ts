import { Component, inject, OnInit, ElementRef, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user';

@Component({
  selector: 'app-flight-header',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './flight-header.html',
  styleUrls: ['./flight-header.scss'],
})
export class FlightHeader implements OnInit {
  private userService = inject(UserService);
  private host = inject(ElementRef);

  nom = '';
  email = '';
  loggedUser: { nom: string; email: string } | null = null;

  showLoginModal = false;
  showUserMenu = false;

  ngOnInit() {
    this.loggedUser = this.userService.getUser();
  }

  openLoginModal() {
    this.showLoginModal = true;
    this.showUserMenu = false;
  }

  closeLoginModal() {
    this.showLoginModal = false;
  }

  login() {
    if (!this.nom || !this.email) return;
    this.userService.setUser({ nom: this.nom, email: this.email });
    this.loggedUser = this.userService.getUser();
    this.nom = '';
    this.email = '';
    this.closeLoginModal();
  }

  toggleUserMenu(event: Event) {
    event.stopPropagation();
    this.showUserMenu = !this.showUserMenu;
  }

  logout() {
    this.userService.logout();
    this.loggedUser = null;
    this.showUserMenu = false;
  }

  
}
