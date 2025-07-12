package med.voll.api.domian.consulta.validaciones;

import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.ConsultaRepository;
import med.voll.api.domian.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorCancelamientoConAnticipacion implements ValidadorCancelamientoDeConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosCancelamientoConsulta datos) {
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidacionException("Una consulta solo puede cancelarse con al menos 24 horas de anticipación.");
        }
    }
}
