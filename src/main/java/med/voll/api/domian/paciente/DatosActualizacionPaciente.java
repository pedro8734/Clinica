package med.voll.api.domian.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domian.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
