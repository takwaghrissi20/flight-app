import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'lib-calendar',
  templateUrl: './calendar.html',
  imports: [CommonModule],
  styleUrls: ['./calendar.scss'], // corrigé : styleUrls au pluriel
})
export class CalendarComponent implements OnInit {

  @Output() dateSelected = new EventEmitter<string>();
  @Output() back = new EventEmitter<void>();

  months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];
  years = [2023, 2024, 2025, 2026, 2027];

  selectedMonth!: number;
  selectedYear!: number;
  selectedDate: number | null = null;

  dates: Array<{ day:number, isGrey:boolean, isSelected?: boolean }> = [];

  ngOnInit(): void {
    const today = new Date();
    this.selectedMonth = today.getMonth();
    this.selectedYear = today.getFullYear();
    this.selectedDate = today.getDate();
    this.generateCalendar();
  }

  generateCalendar(): void {
    this.dates = [];
    const firstDay = new Date(this.selectedYear, this.selectedMonth, 1);
    const lastDay = new Date(this.selectedYear, this.selectedMonth + 1, 0);
    const prevMonthLastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();

    const startDayOfWeek = firstDay.getDay() === 0 ? 7 : firstDay.getDay(); 

    // jours gris du mois précédent
    for (let i = startDayOfWeek - 1; i > 0; i--) {
      this.dates.push({ day: prevMonthLastDay - i + 1, isGrey: true });
    }
    
    // jours du mois courant
    for (let i = 1; i <= lastDay.getDate(); i++) {
      this.dates.push({ day: i, isGrey: false, isSelected: i === this.selectedDate });
    }

    // jours gris du mois suivant pour compléter la grille
    while (this.dates.length % 7 !== 0) {
      this.dates.push({ day: this.dates.length - lastDay.getDate() - (startDayOfWeek - 1), isGrey: true });
    }
  }

  onMonthChange(event: Event): void {
    this.selectedMonth = (event.target as HTMLSelectElement).selectedIndex;
    this.generateCalendar();
  }

  onYearChange(event: Event): void {
    this.selectedYear = parseInt((event.target as HTMLSelectElement).value, 10);
    this.generateCalendar();
  }

  selectDate(date: { day: number, isGrey: boolean }): void {
    if (date.isGrey) return;
    this.selectedDate = date.day;
    this.generateCalendar();
     this.emitSelectedDate();
  }

  emitSelectedDate(): void {
  if (this.selectedDate === null) return;

  const month = (this.selectedMonth + 1).toString().padStart(2,'0');
  const day = this.selectedDate.toString().padStart(2,'0');
  const formatted = `${this.selectedYear}-${month}-${day}`;

  this.dateSelected.emit(formatted);
}

onBack(): void {
  // Just emit the event, parent can hide calendar
  this.back.emit();
}

}
