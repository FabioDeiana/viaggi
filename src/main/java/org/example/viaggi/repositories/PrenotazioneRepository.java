package org.example.viaggi.repositories;

import org.example.viaggi.entities.Dipendente;
import org.example.viaggi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteAndDataRichiesta(Dipendente dipendente, LocalDate dataDiRichiesta);
}
