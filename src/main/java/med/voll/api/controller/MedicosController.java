package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domian.medico.DatosDetallesMedico;
import med.voll.api.domian.medico.DatosListaMedico;
import med.voll.api.domian.medico.DatosActualizacionMedico;
import med.voll.api.domian.medico.DatosRegistroMedico;
import med.voll.api.domian.medico.Medico;
import med.voll.api.domian.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarMedicos(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(datos);

        medicoRepository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return  ResponseEntity.created(uri).body(new DatosDetallesMedico(medico));
    }
    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>> ListarMedico(@PageableDefault(size = 10, sort={"nombre"}) Pageable paginacion){
     var page = medicoRepository.findAllByActivoTrue(paginacion)
             .map(DatosListaMedico :: new);
     return ResponseEntity.ok(page);
    }
    @Transactional
    @PutMapping
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizacionMedico datos){
        var medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetallesMedico(medico));

    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarMedico(@PathVariable Long id){
    var medico = medicoRepository.getReferenceById(id);
    medico.eliminar();

    return  ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detallarMedico(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);

        return  ResponseEntity.ok(new DatosDetallesMedico(medico));
    }
}
