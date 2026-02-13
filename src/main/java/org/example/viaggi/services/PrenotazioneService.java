package org.example.viaggi.services;

import org.example.viaggi.entities.Dipendente;
import org.example.viaggi.entities.Prenotazione;
import org.example.viaggi.entities.Viaggio;
import org.example.viaggi.payload.PrenotazionePayload;
import org.example.viaggi.repositories.DipendenteRepository;
import org.example.viaggi.repositories.PrenotazioneRepository;
import org.example.viaggi.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    // GET tutte le prenotazioni
    public List<Prenotazione> findAll() {
        return prenotazioneRepository.findAll();
    }

    // GET singola prenotazione
    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con id " + id + " non trovata"));
    }

    // POST crea nuova prenotazione
    public Prenotazione save(PrenotazionePayload payload) {
        // cerco il dipendente nel db
        Dipendente dipendente = dipendenteRepository.findById(payload.getDipendenteId())
                .orElseThrow(() -> new RuntimeException("Dipendente con id " + payload.getDipendenteId() + " non trovato"));

        // cerco il viaggio nel db
        Viaggio viaggio = viaggioRepository.findById(payload.getViaggioId())
                .orElseThrow(() -> new RuntimeException("Viaggio con id " + payload.getViaggioId() + " non trovato"));

        // controllo se il dipendente ha già una prenotazione in quella data
        boolean giaPrenotato = prenotazioneRepository
                .existsByDipendenteAndDataDiRichiesta(dipendente, payload.getDataDiRichiesta());

        if (giaPrenotato) {
            throw new RuntimeException("Il dipendente ha già una prenotazione in questa data");
        }

        // creo la prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setDataDiRichiesta(payload.getDataDiRichiesta());
        prenotazione.setNotePreferenze(payload.getNotePreferenze());

        return prenotazioneRepository.save(prenotazione);
    }

    // PUT aggiorna prenotazione
    public Prenotazione update(Long id, Prenotazione prenotazione) {
        prenotazione.setId(id);
        return prenotazioneRepository.save(prenotazione);
    }

    // DELETE cancella prenotazione
    public void delete(Long id) {
        prenotazioneRepository.deleteById(id);
    }
}