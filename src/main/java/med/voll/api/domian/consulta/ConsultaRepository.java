package med.voll.api.domian.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByPacienteIdAndFechaBetween( Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
}
