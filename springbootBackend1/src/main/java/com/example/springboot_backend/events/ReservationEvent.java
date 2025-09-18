package com.example.springboot_backend.events;

import org.springframework.context.ApplicationEvent;

public class ReservationEvent extends ApplicationEvent {

    private final Long volId;
    private final String emailPassager;
    private final Integer placesDemandees;
    private final Integer placesDisponiblesAvant;
    private final String statut; // success/failed
    private final String messageErreur;

    public ReservationEvent(Object source, Long volId, String emailPassager, Integer placesDemandees,
                            Integer placesDisponiblesAvant, String statut, String messageErreur) {
        super(source);
        this.volId = volId;
        this.emailPassager = emailPassager;
        this.placesDemandees = placesDemandees;
        this.placesDisponiblesAvant = placesDisponiblesAvant;
        this.statut = statut;
        this.messageErreur = messageErreur;
    }

    public Long getVolId() { return volId; }
    public String getEmailPassager() { return emailPassager; }
    public Integer getPlacesDemandees() { return placesDemandees; }
    public Integer getPlacesDisponiblesAvant() { return placesDisponiblesAvant; }
    public String getStatut() { return statut; }
    public String getMessageErreur() { return messageErreur; }
}
