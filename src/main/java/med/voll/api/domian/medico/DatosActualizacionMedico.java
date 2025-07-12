package med.voll.api.domian.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domian.direccion.DatosDireccion;

public record DatosActualizacionMedico(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
