package com.example.springboot_backend.models;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "audit_reservation")
public class AuditReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private Long volId;
    private String emailPassager;
    private Integer placesDemandees;
    private Integer placesDisponiblesAvant;
    private String statut; 
    private String messageErreur;

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Long getVolId() { return volId; }
    public void setVolId(Long volId) { this.volId = volId; }

    public String getEmailPassager() { return emailPassager; }
    public void setEmailPassager(String emailPassager) { this.emailPassager = emailPassager; }

    public Integer getPlacesDemandees() { return placesDemandees; }
    public void setPlacesDemandees(Integer placesDemandees) { this.placesDemandees = placesDemandees; }

    public Integer getPlacesDisponiblesAvant() { return placesDisponiblesAvant; }
    public void setPlacesDisponiblesAvant(Integer placesDisponiblesAvant) { this.placesDisponiblesAvant = placesDisponiblesAvant; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getMessageErreur() { return messageErreur; }
    public void setMessageErreur(String messageErreur) { this.messageErreur = messageErreur; }
}
