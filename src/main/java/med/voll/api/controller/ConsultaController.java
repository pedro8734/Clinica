package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domian.consulta.DatosCancelamientoConsulta;
import med.voll.api.domian.consulta.DatosDetalleConsulta;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import med.voll.api.domian.consulta.ReservaDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ReservaDeConsultas reservaDeConsultas;

    @Transactional
    @PostMapping
    public ResponseEntity reservar(@RequestBody @Valid DatosReservaConsulta datos){

        var detalleConsulta =  reservaDeConsultas.reservar(datos);


        return ResponseEntity.ok((detalleConsulta));

    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {
        reservaDeConsultas.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

}
