package med.voll.api.domian.paciente;

import med.voll.api.domian.direccion.Direccion;

public record DatosDetallesPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Direccion direccion

) { public DatosDetallesPaciente(Paciente paciente){
    this(paciente.getId(),
            paciente.getNombre(),
            paciente.getEmail(),
            paciente.getTelefono(),
            paciente.getDocumento(),
            paciente.getDireccion());
}
}
