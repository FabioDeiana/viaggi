package org.example.viaggi.controllers;

import jakarta.validation.Valid;
import org.example.viaggi.entities.Dipendente;
import org.example.viaggi.services.CloudinaryService;
import org.example.viaggi.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // GET /dipendenti
    @GetMapping
    public ResponseEntity<List<Dipendente>> getAllDipendenti() {
        List<Dipendente> dipendenti = dipendenteService.findAll();
        return ResponseEntity.ok(dipendenti);
    }

    // GET /dipendenti/123
    @GetMapping("/{id}")
    public ResponseEntity<?> getDipendenteById(@PathVariable Long id) {
        try {
            Dipendente dipendente = dipendenteService.findById(id);
            return ResponseEntity.ok(dipendente);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /dipendenti
    @PostMapping
    public ResponseEntity<?> createDipendente(@Valid @RequestBody Dipendente dipendente) {
        try {
            Dipendente saved = dipendenteService.save(dipendente);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT /dipendenti/123
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDipendente(@PathVariable Long id,
                                              @RequestBody Dipendente dipendente) {
        try {
            Dipendente updated = dipendenteService.update(id, dipendente);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE /dipendenti/123
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDipendente(@PathVariable Long id) {
        try {
            dipendenteService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST /dipendenti/123/immagine - upload immagine profilo
    @PostMapping("/{id}/immagine")
    public ResponseEntity<?> uploadImmagine(@PathVariable Long id,
                                            @RequestParam("immagine") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadImage(file);
            Dipendente dipendente = dipendenteService.findById(id);
            dipendente.setImmagineProfilo(url);
            dipendenteService.update(id, dipendente);
            return ResponseEntity.ok("Immagine aggiornata: " + url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}