package org.example.viaggi.controllers;

import jakarta.validation.Valid;
import org.example.viaggi.entities.Prenotazione;
import org.example.viaggi.payload.PrenotazionePayload;
import org.example.viaggi.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    // GET /prenotazioni
    @GetMapping
    public ResponseEntity<List<Prenotazione>> getAllPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneService.findAll();
        return ResponseEntity.ok(prenotazioni);
    }

    // GET /prenotazioni/123
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrenotazioneById(@PathVariable Long id) {
        try {
            Prenotazione prenotazione = prenotazioneService.findById(id);
            return ResponseEntity.ok(prenotazione);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /prenotazioni
    @PostMapping
    public ResponseEntity<?> createPrenotazione(@Valid @RequestBody PrenotazionePayload payload) {
        try {
            Prenotazione saved = prenotazioneService.save(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT /prenotazioni/123
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrenotazione(@PathVariable Long id,
                                                @RequestBody Prenotazione prenotazione) {
        try {
            Prenotazione updated = prenotazioneService.update(id, prenotazione);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE /prenotazioni/123
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrenotazione(@PathVariable Long id) {
        try {
            prenotazioneService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}