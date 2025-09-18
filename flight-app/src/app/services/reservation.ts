import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RESERVATIONS_URL } from '../models/constants/urls';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

   private http = inject(HttpClient);

  reserve(reservation: { volId: number; passager: { nom: string; email: string }; nombrePlaces: number }): Observable<any> {

    
    return this.http.post(RESERVATIONS_URL, reservation );
    
  }
  
}
