package org.example.viaggi.controllers;

import jakarta.validation.Valid;
import org.example.viaggi.entities.Viaggio;
import org.example.viaggi.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    // GET /viaggi
    @GetMapping
    public ResponseEntity<List<Viaggio>> getAllViaggi() {
        List<Viaggio> viaggi = viaggioService.findAll();
        return ResponseEntity.ok(viaggi);
    }

    // GET /viaggi/123
    @GetMapping("/{id}")
    public ResponseEntity<?> getViaggioById(@PathVariable Long id) {
        try {
            Viaggio viaggio = viaggioService.findById(id);
            return ResponseEntity.ok(viaggio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /viaggi
    @PostMapping
    public ResponseEntity<?> createViaggio(@Valid @RequestBody Viaggio viaggio) {
        try {
            Viaggio saved = viaggioService.save(viaggio);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT /viaggi/123
    @PutMapping("/{id}")
    public ResponseEntity<?> updateViaggio(@PathVariable Long id,
                                           @RequestBody Viaggio viaggio) {
        try {
            Viaggio updated = viaggioService.update(id, viaggio);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PATCH /viaggi/123/stato - cambia stato viaggio
    @PatchMapping("/{id}/stato")
    public ResponseEntity<?> cambiaStato(@PathVariable Long id,
                                         @RequestParam boolean stato) {
        try {
            Viaggio updated = viaggioService.cambiaStato(id, stato);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE /viaggi/123
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViaggio(@PathVariable Long id) {
        try {
            viaggioService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}