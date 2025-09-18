package com.example.springboot_backend.models;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationCode;

    private String nomPassager;
    private String emailPassager;
    private Integer nombrePlaces;

    @ManyToOne
    @JoinColumn(name = "vol_id", nullable = false)
    private Vol vol;

    public Reservation() {
        this.reservationCode = UUID.randomUUID().toString(); 
    }

    
    public Long getId() { return id; }

    public String getReservationCode() { return reservationCode; }
    public void setReservationCode(String reservationCode) { this.reservationCode = reservationCode; }

    public String getNomPassager() { return nomPassager; }
    public void setNomPassager(String nomPassager) { this.nomPassager = nomPassager; }

    public String getEmailPassager() { return emailPassager; }
    public void setEmailPassager(String emailPassager) { this.emailPassager = emailPassager; }

    public Integer getNombrePlaces() { return nombrePlaces; }
    public void setNombrePlaces(Integer nombrePlaces) { this.nombrePlaces = nombrePlaces; }

    public Vol getVol() { return vol; }
    public void setVol(Vol vol) { this.vol = vol; }
}
