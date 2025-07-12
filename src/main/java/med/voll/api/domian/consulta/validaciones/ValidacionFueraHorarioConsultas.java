package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraHorarioConsultas implements  ValidadorDeConsulta {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesDeAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierreClinica =  fechaConsulta.getHour() > 18;

        if(domingo || horarioAntesDeAperturaClinica || horarioDespuesDeCierreClinica){
            throw  new ValidacionException("Horario seleccionado fuera del horario de atencion de la clinica");
        }
    }
}
