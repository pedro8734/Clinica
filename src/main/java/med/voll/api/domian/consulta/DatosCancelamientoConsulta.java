package med.voll.api.domian.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamiento motivo

) {
}
