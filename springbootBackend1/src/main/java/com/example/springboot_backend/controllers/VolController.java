package com.example.springboot_backend.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_backend.models.Vol;
import com.example.springboot_backend.services.VolService;

@RestController
@RequestMapping("/api/vols")

public class VolController {
    private final VolService volService;

    public VolController(VolService volService) {
        this.volService = volService;
    }

    
    @GetMapping
    public List<Vol> getVols(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateArrivee,
            @RequestParam(required = false) String villeDepart,
            @RequestParam(required = false) String villeArrivee,
            @RequestParam(required = false) String sortBy
    ) {
        return volService.getVols(dateDepart, dateArrivee, villeDepart, villeArrivee, sortBy);
    }

    
    @PostMapping
    public List<Vol> addVols(@RequestBody List<Vol> vols) {
        return volService.addVols(vols);
    }
    
    @GetMapping("/{id}/places")
    public Integer getPlacesDisponibles(@PathVariable Long id) {
        return volService.getPlacesDisponibles(id);
    }
}