package med.voll.api.domian.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
       @NotBlank String calle,
        String numero,
        String complemento,
       @NotBlank String barrio,
       @NotBlank @Pattern(regexp = "\\d{6}") String codigo_postal,
       @NotBlank String ciudad,
       @NotBlank String estado
) {
}
