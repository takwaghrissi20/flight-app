import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CalendarComponent } from '@flight-app/calendar';
import { CommonModule } from '@angular/common';
import { Vol } from '../../models/vol.model';
import { LoadingService } from '../../services/loading';
import { LoadingComponent } from "../loading/loading";
import { NotFound } from "../not-found/not-found";
import { FlightService } from '../../services/flight';
import { take } from 'rxjs';
import { FlightHeader } from "../flight-header/flight-header";
import { ReservationModal } from "../reservation-modal/reservation-modal";
import { ReservationService } from '../../services/reservation';
import { ReservationButton } from "../reservation-button/reservation-button";
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../../services/user';


@Component({
  selector: 'app-flight-search',
  standalone: true,
  imports: [CommonModule, FormsModule, CalendarComponent, LoadingComponent, NotFound, FlightHeader, ReservationModal, ReservationButton],
  templateUrl: './flight-search.html',
  styleUrls: ['./flight-search.scss'],
})
export class FlightSearchComponent implements OnInit {
  villeDepart = '';
  villeArrivee = '';
  dateDepart: string | null = null;
  dateArrivee: string | null = null;
  critereTri: 'prix' | 'tempsTrajet' = 'prix';
  vols: Vol[] = [];

  showModal = false;
  selectedVol: Vol | null = null;

  private reservationService = inject(ReservationService);
  private toastr = inject(ToastrService)

  showDepartCalendar = false;
  showArriveeCalendar = false;
  isCompact = false;
  hasSearched = false;

  invalidFields: { villeDepart: boolean; villeArrivee: boolean; dateDepart: boolean } = {
  villeDepart: false,
  villeArrivee: false,
  dateDepart: false,
};
  reservationsEffectuees: { [key: string]: boolean } = {};
  selectedReservationData: { nom: string; email: string; seats: number } | null = null;

  private loadingService = inject(LoadingService);
  private flightservice = inject(FlightService);
  private userService = inject(UserService)

  ngOnInit() {
    // Any initialization logic that doesn't fetch data
    console.log('Flight search component initialized');
  }

  async rechercherVols() {
    this.hasSearched = true;

     this.invalidFields = { villeDepart: false, villeArrivee: false, dateDepart: false };

  // check required fields
  if (!this.villeDepart || !this.villeArrivee || !this.dateDepart) {
    if (!this.villeDepart) this.invalidFields.villeDepart = true;
    if (!this.villeArrivee) this.invalidFields.villeArrivee = true;
    if (!this.dateDepart) this.invalidFields.dateDepart = true;

    this.toastr.error("Veuillez remplir Ville départ, Ville arrivée et Date départ !");
    return;
  }

    this.loadingService.show(); 
    
    this.flightservice
    .searchVols(
      this.villeDepart,
      this.villeArrivee,
      this.dateDepart,
      this.dateArrivee,
      this.critereTri
    )
    .pipe(take(1))
    .subscribe({
      next: (data:Vol[]) => {
        this.vols = data;
        this.isCompact = this.vols.length > 0;
      },
    error: (err) =>{
      console.error('Erreur fetch vols:', err);
      this.vols = [];
    }, 
    complete: () => {
      this.loadingService.hide(); // hide loader
    }
  });
  }

   onReserve(reservation: { seats: number; nom: string; email: string ;}) {
    if (!this.selectedVol) return;

     const key = `${this.selectedVol.id}-${reservation.email}`;
  if (this.reservationsEffectuees[key]) {
    this.toastr.error("Vous avez déjà réservé ce vol !");
    return;
  }

    const body = {
    volId: this.selectedVol.id,
    passager: {
      nom: reservation.nom,
      email: reservation.email
    },
    nombrePlaces: reservation.seats
  };
  console.log(JSON.stringify(body));

    this.reservationService.reserve(body).subscribe({
      next: () => {
        this.toastr.success("Réservation confirmée !");
        this.showModal = false;

        this.reservationsEffectuees[key] = true;

        if (this.selectedVol) {
          this.selectedVol.placesDisponibles -= reservation.seats;
        }
        
      },
      error: (err) => {
      if (err.status === 400) {
         console.error('Erreur backend:', err);
        const message = err.error?.message || "Désolé, plus assez de places !";
        this.toastr.error(message);
      } else {
        this.toastr.error("Erreur lors de la réservation.");
      }
    }
    });
  }

  onDepartSelected(date: string) {
    this.dateDepart = date;
    this.showDepartCalendar = false;
  }

  onArriveeSelected(date: string) {
    this.dateArrivee = date;
    this.showArriveeCalendar = false;
  }

  formatTemps(trajet: number) {
    const h = Math.floor(trajet / 60);
    const min = trajet % 60;
    return `${h}h ${min.toString().padStart(2, '0')}min`;
  }

  swapVilles() {
    [this.villeDepart, this.villeArrivee] = [this.villeArrivee, this.villeDepart];
  }

    openReservation(vol: Vol) {
    this.selectedVol = vol;
    this.showModal = true;

    const user = this.userService.getUser();
    if(user){
      this.selectedReservationData={
        nom:user.nom,
        email:user.email,
        seats:1
      }
    }
  }

  expandSearchBar() {
  this.isCompact = false;
  this.villeDepart = '';
  this.villeArrivee = '';
  this.dateDepart = null;
  this.dateArrivee = null;
  this.critereTri = 'prix';
  this.vols = [];
  this.isCompact = false;
  this.hasSearched = false;
  this.invalidFields = { villeDepart: false, villeArrivee: false, dateDepart: false };
  this.selectedVol = null;
  this.selectedReservationData = null;
}

}


