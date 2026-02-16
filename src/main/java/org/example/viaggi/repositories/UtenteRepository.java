package org.example.viaggi.repositories;

import org.example.viaggi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    // cerca utente per email (serve per il login)
    Optional<Utente> findByEmail(String email);
}