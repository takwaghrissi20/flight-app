package com.example.springboot_backend.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_backend.events.ReservationEvent;
import com.example.springboot_backend.exceptions.InsufficientSeatsException;
import com.example.springboot_backend.models.Reservation;
import com.example.springboot_backend.models.Vol;
import com.example.springboot_backend.repositories.ReservationRepository;
import com.example.springboot_backend.repositories.VolRepository;
import com.example.springboot_backend.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    @Retryable(
        value = OptimisticLockingFailureException.class,
        maxAttempts = 5,
        backoff = @Backoff(delay = 100)
    )
    @CacheEvict(value = "placesDisponibles", key = "#volId")
    public Reservation reserver(Long volId, String nom, String email, Integer nombrePlaces) {
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new RuntimeException("Vol introuvable avec id: " + volId));

        
        if (vol.getPlacesDisponibles() == null) {
            vol.setPlacesDisponibles(vol.getCapacite());
            volRepository.save(vol);
        }

        Integer placesAvant = vol.getPlacesDisponibles();

        try {
            
            if (vol.getPlacesDisponibles() < nombrePlaces) {
                throw new InsufficientSeatsException(
                    "Pas assez de places disponibles. Places restantes : " + vol.getPlacesDisponibles()
                );
            }

            
            vol.setPlacesDisponibles(vol.getPlacesDisponibles() - nombrePlaces);
            volRepository.save(vol);

            
            Reservation reservation = new Reservation();
            reservation.setVol(vol);
            reservation.setNomPassager(nom);
            reservation.setEmailPassager(email);
            reservation.setNombrePlaces(nombrePlaces);

            Reservation saved = reservationRepository.save(reservation);

            
            eventPublisher.publishEvent(new ReservationEvent(this,
                    volId, email, nombrePlaces, placesAvant, "success", null));

            return saved;

        } catch (Exception ex) {
            
            eventPublisher.publishEvent(new ReservationEvent(this,
                    volId, email, nombrePlaces, placesAvant, "failed", ex.getMessage()));
            throw ex; 
        }
    }
}
