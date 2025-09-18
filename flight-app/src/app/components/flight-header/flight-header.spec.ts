import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FlightHeader } from './flight-header';

describe('FlightHeader', () => {
  let component: FlightHeader;
  let fixture: ComponentFixture<FlightHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlightHeader],
    }).compileComponents();

    fixture = TestBed.createComponent(FlightHeader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
