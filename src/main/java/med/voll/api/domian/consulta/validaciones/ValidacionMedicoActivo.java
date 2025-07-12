package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import med.voll.api.domian.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoActivo implements  ValidadorDeConsulta{
    @Autowired
    private MedicoRepository medicoRepository;

    public void  validar(DatosReservaConsulta datos){
        // medico es opcional
        if (datos.idMedico() == null){
            return ;
        }

        var medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico());
        if(!medicoEstaActivo){
            throw  new ValidacionException("Consulta no puede ser reservada con paciente excluido");
        }

    }
}
