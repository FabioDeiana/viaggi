package org.example.viaggi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ogni prenotazione ha un viaggio
    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    // ogni prenotazione appartiene a un dipendente
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    private LocalDate dataDiRichiesta;
    private String notePreferenze;
}
