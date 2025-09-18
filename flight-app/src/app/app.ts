import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxWelcome } from './nx-welcome';
import { LoadingComponent } from './components/loading/loading';
import { FlightSearchComponent } from "./components/flight-search/flight-search";


@Component({
  imports: [NxWelcome, RouterModule, LoadingComponent, FlightSearchComponent],
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected title = 'flight-app';
}
