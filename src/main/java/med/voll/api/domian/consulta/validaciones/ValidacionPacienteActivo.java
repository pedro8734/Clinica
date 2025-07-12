package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import med.voll.api.domian.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionPacienteActivo implements  ValidadorDeConsulta {
    @Autowired
    private PacienteRepository pacienteRepository;

    public void  validar(DatosReservaConsulta datos){
    var pacienteEstaActivo = pacienteRepository.findActivoById(datos.idPaciente());
    if(pacienteEstaActivo == null || !pacienteEstaActivo){
        throw  new ValidacionException("Consulta no puede ser reservada con paciente excluido");
    }

    }
}
