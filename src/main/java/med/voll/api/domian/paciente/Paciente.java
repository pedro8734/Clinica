package med.voll.api.domian.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domian.direccion.Direccion;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name ="Paciente")
@Table(name ="pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos){
        this.id = null;
        this.activo = true;
        this.nombre= datos.nombre();
        this.email= datos.email();
        this.telefono = datos.telefono();
        this.documento= datos.documento();
        this.direccion = new Direccion(datos.direccion());

    }

    public void actualizarInformacion(@Valid DatosActualizacionPaciente datos) {
        if (datos.nombre() != null){
            this.nombre= datos.nombre();
        }
        if (datos.telefono() != null){
            this.telefono= datos.telefono();
        }
        if (datos.direccion() != null){
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }
    public void eliminar() {
        this.activo = false;
    }
}
