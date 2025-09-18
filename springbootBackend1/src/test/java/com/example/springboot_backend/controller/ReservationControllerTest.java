package com.example.springboot_backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.springboot_backend.models.Vol;
import com.example.springboot_backend.repositories.VolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
	
	 @LocalServerPort
	    private int port;

	    @Autowired
	    private VolRepository volRepository;

	    @Autowired
	    private TestRestTemplate restTemplate;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Test
	    void testReservationFluxComplet() throws Exception {
	        // 1️⃣ Créer un vol
	        Vol vol = new Vol();
	        vol.setDateDepart(LocalDate.now().plusDays(1));
	        vol.setDateArrivee(LocalDate.now().plusDays(2));
	        vol.setVilleDepart("Paris");
	        vol.setVilleArrivee("Lyon");
	        vol.setCapacite(5);
	        vol.setPlacesDisponibles(5);
	        vol.setPrix(100.0);
	        volRepository.save(vol);

	        
	        String url = "http://localhost:" + port + "/api/reservations";
	        String body = """
	            {
	              "volId": %d,
	              "passager": { "nom": "Alice", "email": "alice@mail.com" },
	              "nombrePlaces": 3
	            }
	            """.formatted(vol.getId());

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(body, headers);

	        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());

	        
	        Vol updatedVol = volRepository.findById(vol.getId()).orElseThrow();
	        assertEquals(2, updatedVol.getPlacesDisponibles());

	        
	        body = """
	            {
	              "volId": %d,
	              "passager": { "nom": "Bob", "email": "bob@mail.com" },
	              "nombrePlaces": 5
	            }
	            """.formatted(vol.getId());

	        entity = new HttpEntity<>(body, headers);
	        ResponseEntity<String> response2 = restTemplate.postForEntity(url, entity, String.class);
	        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
	    }

}
