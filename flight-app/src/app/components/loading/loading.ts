import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { inject } from '@angular/core';
import { LoadingService } from '../../services/loading';

@Component({
  selector: 'app-loading',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './loading.html',
  styleUrls: ['./loading.scss'],
})
export class LoadingComponent {
  private loadingService = inject(LoadingService);
  isLoading = false;

  constructor() {
    this.loadingService.isLoading.subscribe((status) => {
      this.isLoading = status;
    });
  }
}
