package com.example.springboot_backend.services;

import com.example.springboot_backend.models.Vol;

import java.time.LocalDate;
import java.util.List;

public interface VolService {
    List<Vol> getVols(LocalDate dateDepart, LocalDate dateArrivee,
                      String villeDepart, String villeArrivee,
                      String sortBy);

    List<Vol> addVols(List<Vol> vols);
    Integer getPlacesDisponibles(Long volId);
  
}
