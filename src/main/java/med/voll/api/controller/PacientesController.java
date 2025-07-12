package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domian.paciente.DatosActualizacionPaciente;
import med.voll.api.domian.paciente.DatosDetallesPaciente;
import med.voll.api.domian.paciente.DatosListaPaciente;
import med.voll.api.domian.paciente.DatosRegistroPaciente;
import med.voll.api.domian.paciente.Paciente;
import med.voll.api.domian.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacientesController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarPacientes(@RequestBody DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(datos);
        pacienteRepository.save(paciente);
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
        return  ResponseEntity.created(uri).body(new DatosDetallesPaciente(paciente));

    }
    @GetMapping
    public  ResponseEntity<Page<DatosListaPaciente>> ListarPaciente(@PageableDefault(size = 10, sort={"nombre"}) Pageable paginacion){
        var page =  pacienteRepository.findAllByActivoTrue(paginacion)
                .map(DatosListaPaciente :: new);
        return ResponseEntity.ok(page);
    }
    @Transactional
    @PutMapping
    public  ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizacionPaciente datos){
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
        return  ResponseEntity.ok(new DatosDetallesPaciente(paciente));

    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        var medico = pacienteRepository.getReferenceById(id);
        medico.eliminar();
        return  ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detallarPaciente(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);

        return  ResponseEntity.ok(new DatosDetallesPaciente(paciente));
    }

}
