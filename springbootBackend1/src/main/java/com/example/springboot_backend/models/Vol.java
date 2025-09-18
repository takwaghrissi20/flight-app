package com.example.springboot_backend.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Vol")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDepart;
    private LocalDate dateArrivee;
    private String villeDepart;
    private String villeArrivee;
    private Double prix;
    private Integer tempsTrajet; 

    
    private Integer capacite;          
    private Integer placesDisponibles; 
    
    @Version
    private Integer version = 0;
    
    public Vol() {}

   
    @PrePersist
    public void initPlacesDisponibles() {
        if (placesDisponibles == null && capacite != null) {
            placesDisponibles = capacite;
        }
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }

    public LocalDate getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDate dateArrivee) { this.dateArrivee = dateArrivee; }

    public String getVilleDepart() { return villeDepart; }
    public void setVilleDepart(String villeDepart) { this.villeDepart = villeDepart; }

    public String getVilleArrivee() { return villeArrivee; }
    public void setVilleArrivee(String villeArrivee) { this.villeArrivee = villeArrivee; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public Integer getTempsTrajet() { return tempsTrajet; }
    public void setTempsTrajet(Integer tempsTrajet) { this.tempsTrajet = tempsTrajet; }

    public Integer getCapacite() { return capacite; }
    public void setCapacite(Integer capacite) { this.capacite = capacite; }

    public Integer getPlacesDisponibles() { return placesDisponibles; }
    public void setPlacesDisponibles(Integer placesDisponibles) { this.placesDisponibles = placesDisponibles; }
}
