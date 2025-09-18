import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-reservation-button',
  imports: [],
  templateUrl: './reservation-button.html',
  styleUrl: './reservation-button.scss',
})
export class ReservationButton {
  @Input() volId!: number;
  @Input() disabled = false;
  @Output() reserve = new EventEmitter<number>();

  onClick() {
    this.reserve.emit(this.volId);
  }
}
