package com.example.springboot_backend.listeners;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springboot_backend.events.ReservationEvent;
import com.example.springboot_backend.models.AuditReservation;
import com.example.springboot_backend.repositories.AuditReservationRepository;

@Component
public class ReservationAuditListener {

    @Autowired
    private AuditReservationRepository auditRepository;

    @EventListener
    public void handleReservationEvent(ReservationEvent event) {
        AuditReservation audit = new AuditReservation();
        audit.setTimestamp(LocalDateTime.now());
        audit.setVolId(event.getVolId());
        audit.setEmailPassager(event.getEmailPassager());
        audit.setPlacesDemandees(event.getPlacesDemandees());
        audit.setPlacesDisponiblesAvant(event.getPlacesDisponiblesAvant());
        audit.setStatut(event.getStatut());
        audit.setMessageErreur(event.getMessageErreur());

        auditRepository.save(audit);
    }
}

