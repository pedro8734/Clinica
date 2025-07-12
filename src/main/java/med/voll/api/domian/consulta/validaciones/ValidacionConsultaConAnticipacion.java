package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaConAnticipacion implements  ValidadorDeConsulta {

    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora , fechaConsulta).toMinutes();

        if(diferenciaEnMinutos < 30){
            throw  new ValidacionException("Hoario seleccionado con menor de 30 minutos de anticipacion");
        }

    }
}
