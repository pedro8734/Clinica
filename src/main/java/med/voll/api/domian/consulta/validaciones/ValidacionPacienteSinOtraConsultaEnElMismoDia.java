package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.ConsultaRepository;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionPacienteSinOtraConsultaEnElMismoDia implements  ValidadorDeConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void  validar(DatosReservaConsulta datos){

        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnElDia= consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),primerHorario,ultimoHorario);
        if(pacienteTieneOtraConsultaEnElDia){
            throw  new ValidacionException("Paciente tiene una consulta ya reservada para ese dia");
        }
    }
}
