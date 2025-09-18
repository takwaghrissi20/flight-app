package com.example.springboot_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot_backend.models.Vol;

@Repository
public interface VolRepository extends JpaRepository<Vol, Long> {
}
