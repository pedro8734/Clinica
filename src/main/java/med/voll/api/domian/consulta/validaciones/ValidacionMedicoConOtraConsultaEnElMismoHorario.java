package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.Consulta;
import med.voll.api.domian.consulta.ConsultaRepository;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoConOtraConsultaEnElMismoHorario implements  ValidadorDeConsulta{
    @Autowired
    private ConsultaRepository consultaRepository;
    public void  validar(DatosReservaConsulta datos){

        var medicoTieneOtraConsultaEnElMismoHorario= consultaRepository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if(medicoTieneOtraConsultaEnElMismoHorario){
            throw  new ValidacionException("Medico  tiene otra  consulta en  ese misma fecha y hora");
        }
    }
}
