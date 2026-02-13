package org.example.viaggi.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PrenotazionePayload {

    @NotNull(message = "L'id del viaggio è obbligatorio")
    private Long viaggioId;

    @NotNull(message = "L'id del dipendente è obbligatorio")
    private Long dipendenteId;

    @NotNull(message = "La data di richiesta è obbligatoria")
    private LocalDate dataDiRichiesta;

    private String notePreferenze;
}
