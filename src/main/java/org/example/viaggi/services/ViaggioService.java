package org.example.viaggi.services;

import org.example.viaggi.entities.Viaggio;
import org.example.viaggi.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    // GET tutti i viaggi
    public List<Viaggio> findAll() {
        return viaggioRepository.findAll();
    }

    // GET singolo viaggio
    public Viaggio findById(Long id) {
        return viaggioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaggio con id " + id + " non trovato"));
    }

    // POST crea nuovo viaggio
    public Viaggio save(Viaggio viaggio) {
        // di default il viaggio è in programma
        viaggio.setStato(false);
        return viaggioRepository.save(viaggio);
    }

    // PUT aggiorna viaggio
    public Viaggio update(Long id, Viaggio viaggio) {
        viaggio.setId(id);
        return viaggioRepository.save(viaggio);
    }

    // PATCH cambia stato viaggio
    public Viaggio cambiaStato(Long id, boolean stato) {
        Viaggio viaggio = findById(id);
        viaggio.setStato(stato);
        return viaggioRepository.save(viaggio);
    }

    // DELETE cancella viaggio
    public void delete(Long id) {
        viaggioRepository.deleteById(id);
    }
}