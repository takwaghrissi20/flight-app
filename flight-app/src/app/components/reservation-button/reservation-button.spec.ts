import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReservationButton } from '../reservation-button';

describe('ReservationButton', () => {
  let component: ReservationButton;
  let fixture: ComponentFixture<ReservationButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationButton],
    }).compileComponents();

    fixture = TestBed.createComponent(ReservationButton);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
