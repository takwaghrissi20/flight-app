package com.example.springboot_backend.servicesImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import com.example.springboot_backend.exceptions.InsufficientSeatsException;
import com.example.springboot_backend.models.Reservation;
import com.example.springboot_backend.models.Vol;
import com.example.springboot_backend.repositories.ReservationRepository;
import com.example.springboot_backend.repositories.VolRepository;

public  class  ReservationServiceImplTest {
	
	 @InjectMocks
	    private ReservationServiceImpl reservationService;

	    @Mock
	    private ReservationRepository reservationRepository;

	    @Mock
	    private VolRepository volRepository;
	    
	    @Mock
	    private ApplicationEventPublisher eventPublisher;

	    @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testReservationSucces() {
	        Vol vol = new Vol();
	        vol.setId(1L);
	        vol.setCapacite(10);
	        vol.setPlacesDisponibles(5);

	        when(volRepository.findById(1L)).thenReturn(java.util.Optional.of(vol));
	        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

	        Reservation res = reservationService.reserver(1L, "Alice", "alice@mail.com", 3);

	        assertEquals(2, vol.getPlacesDisponibles());
	        assertEquals("Alice", res.getNomPassager());
	    }

	    @Test
	    void testReservationEchecPasAssezDePlaces() {
	        Vol vol = new Vol();
	        vol.setId(1L);
	        vol.setCapacite(10);
	        vol.setPlacesDisponibles(2);

	        when(volRepository.findById(1L)).thenReturn(java.util.Optional.of(vol));

	        assertThrows(InsufficientSeatsException.class,
	                     () -> reservationService.reserver(1L, "Bob", "bob@mail.com", 5));
	    }
	    

	    
	    @Test
	    void testReservationConcurrente_CompletableFuture() throws ExecutionException, InterruptedException {
	        Vol vol = new Vol();
	        vol.setId(1L);
	        vol.setCapacite(5);
	        vol.setPlacesDisponibles(5);

	        when(volRepository.findById(1L)).thenReturn(java.util.Optional.of(vol));
	        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

	        List<CompletableFuture<Void>> futures =
	            java.util.stream.IntStream.range(0, 10)
	                .mapToObj(i -> CompletableFuture.runAsync(() -> {
	                    try {
	                        reservationService.reserver(1L, "User" + i, "user" + i + "@mail.com", 1);
	                    } catch (InsufficientSeatsException e) {
	                        // attendu quand plus de places
	                    }
	                }))
	                .toList();

	        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();

	        
	        assertEquals(true, vol.getPlacesDisponibles() >= 0);
	    }

	  
	    @RepeatedTest(10)
	    void testReservationConcurrente_RepeatedTest() {
	        Vol vol = new Vol();
	        vol.setId(1L);
	        vol.setCapacite(5);
	        vol.setPlacesDisponibles(5);

	        when(volRepository.findById(1L)).thenReturn(java.util.Optional.of(vol));
	        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

	        try {
	            reservationService.reserver(1L, "RepeatedUser", "user@mail.com", 1);
	        } catch (InsufficientSeatsException e) {
	            
	        }

	        assertEquals(true, vol.getPlacesDisponibles() >= 0);
	    }

}
