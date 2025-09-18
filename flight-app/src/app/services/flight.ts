import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vol } from '../models/vol.model';
import { FLIGHTS_URL } from '../models/constants/urls';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private http = inject(HttpClient);

  searchVols(
    villeDepart: string,
    villeArrivee: string,
    dateDepart: string | null,
    dateArrivee: string | null,
    critereTri: 'prix' | 'tempsTrajet'
  ): Observable<Vol[]> {
    let params = new HttpParams()
    .set('villeDepart', villeDepart)
    .set('villeArrivee', villeArrivee)
    .set('sortBy',critereTri );


    if (dateDepart) {
      params = params.set('dateDepart', dateDepart);
    }

  if (dateArrivee) {
    params = params.set('dateArrivee', dateArrivee);
  }

  return this.http.get<Vol[]>(FLIGHTS_URL, { params });
}
}