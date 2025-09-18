import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { UserService } from '../../services/user';

@Component({
  selector: 'app-reservation-modal',
  imports: [CommonModule ,FormsModule ],
  templateUrl: './reservation-modal.html',
  styleUrl: './reservation-modal.scss',
})
export class ReservationModal {
  @Input() visible = false;
  @Input() volId!: number;
  @Input() placesDisponibles!: number;
  @Output() dismiss = new EventEmitter<void>();
  @Output() confirm = new EventEmitter<{ seats: number; nom: string; email: string }>();


  private userService = inject(UserService)
seats = 1;
nom = this.userService.getUser()?.nom || '';
email = this.userService.getUser()?.email || '';

  onConfirm() {
    if (this.seats <= this.placesDisponibles && this.seats > 0 && this.nom && this.email) {
      this.confirm.emit({
        seats: this.seats,
        nom: this.nom,
        email: this.email
      });
    }
  }
}
