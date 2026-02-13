package org.example.viaggi.services;

import org.example.viaggi.entities.Dipendente;
import org.example.viaggi.repositories.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    // GET tutti i dipendenti
    public List<Dipendente> findAll() {
        return dipendenteRepository.findAll();
    }

    // GET singolo dipendente
    public Dipendente findById(Long id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dipendente con id " + id + " non trovato"));
    }

    // POST crea nuovo dipendente
    public Dipendente save(Dipendente dipendente) {
        return dipendenteRepository.save(dipendente);
    }

    // PUT aggiorna dipendente
    public Dipendente update(Long id, Dipendente dipendente) {
        dipendente.setId(id);
        return dipendenteRepository.save(dipendente);
    }

    // DELETE cancella dipendente
    public void delete(Long id) {
        dipendenteRepository.deleteById(id);
    }
}