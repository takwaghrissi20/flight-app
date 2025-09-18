package com.example.springboot_backend.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.springboot_backend.exceptions.VolNotFoundException;
import com.example.springboot_backend.models.Vol;
import com.example.springboot_backend.repositories.VolRepository;
import com.example.springboot_backend.services.VolService;

@Service
public class VolServiceImpl implements VolService {

    private final VolRepository volRepository;

    @Autowired
    public VolServiceImpl(VolRepository volRepository) {
        this.volRepository = volRepository;
    }

    @Override
    public List<Vol> getVols(LocalDate dateDepart, LocalDate dateArrivee,
                             String villeDepart, String villeArrivee,
                             String sortBy) {
    	
    	LocalDate today = LocalDate.now();

        if (dateDepart != null && dateDepart.isBefore(today)) {
            throw new IllegalArgumentException("La date de départ doit être aujourd'hui ou dans le futur.");
        }

        if (dateArrivee != null && dateDepart != null && dateArrivee.isBefore(dateDepart)) {
            throw new IllegalArgumentException("La date d'arrivée doit être après la date de départ.");
        }

        List<Vol> vols = volRepository.findAll();

        
        if (dateDepart != null) {
            vols = vols.stream()
                       .filter(v -> 
                      
                       !v.getDateDepart().isBefore(dateDepart))
                       .collect(Collectors.toList());
        }

        if (dateArrivee != null) {
            vols = vols.stream()
                       .filter(v -> !v.getDateArrivee().isAfter(dateArrivee))
                       .collect(Collectors.toList());
        }
        

        if (villeDepart != null && !villeDepart.isEmpty()) {
            vols = vols.stream()
                       .filter(v -> v.getVilleDepart().equalsIgnoreCase(villeDepart))
                       .collect(Collectors.toList());
        }

        if (villeArrivee != null && !villeArrivee.isEmpty()) {
            vols = vols.stream()
                       .filter(v -> v.getVilleArrivee().equalsIgnoreCase(villeArrivee))
                       .collect(Collectors.toList());
        }

        
        if ("prix".equalsIgnoreCase(sortBy)) {
            vols = vols.stream()
                       .sorted((a, b) -> a.getPrix().compareTo(b.getPrix()))
                       .collect(Collectors.toList());
        } else if ("tempsTrajet".equalsIgnoreCase(sortBy)) {
            vols = vols.stream()
                       .sorted((a, b) -> a.getTempsTrajet().compareTo(b.getTempsTrajet()))
                       .collect(Collectors.toList());
        }

        return vols;
    }

    @Override
    public List<Vol> addVols(List<Vol> vols) {
        return volRepository.saveAll(vols);
    }

   
    @Override
    @Cacheable(value = "placesDisponibles", key = "#volId")
    public Integer getPlacesDisponibles(Long volId) {
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new VolNotFoundException("Vol introuvable avec id: " + volId));
        return vol.getPlacesDisponibles();
    }
}
