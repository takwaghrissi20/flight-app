import { Component, Input, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-not-found',
  imports: [RouterModule , CommonModule ],
  templateUrl: './not-found.html',
  styleUrl: './not-found.scss',
})
export class NotFound implements OnInit {
  @Input()
  visible= false;
  @Input()
  notFoundMessage = 'Nothing Found!';
  
  

  ngOnInit(): void {
    console.log('Not found');
  }

}
