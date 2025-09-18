package com.example.springboot_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReservationRequest {

    @NotNull(message = "volId obligatoire")
    private Long volId;

    @Valid
    @NotNull(message = "Passager obligatoire")
    private Passager passager;

    @NotNull(message = "Nombre de places obligatoire")
    @Positive(message = "Nombre de places doit être positif")
    private Integer nombrePlaces;

    public static class Passager {

        @NotBlank(message = "Nom du passager obligatoire")
        private String nom;

        @Email(message = "Email invalide")
        @NotBlank(message = "Email obligatoire")
        private String email;

        
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    
    public Long getVolId() { return volId; }
    public void setVolId(Long volId) { this.volId = volId; }

    public Passager getPassager() { return passager; }
    public void setPassager(Passager passager) { this.passager = passager; }

    public Integer getNombrePlaces() { return nombrePlaces; }
    public void setNombrePlaces(Integer nombrePlaces) { this.nombrePlaces = nombrePlaces; }
}
