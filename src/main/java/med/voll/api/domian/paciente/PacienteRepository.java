package med.voll.api.domian.paciente;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByActivoTrue(Pageable paginacion);
    @Query("""
            select p.activo
            from Paciente p
            where
            p.id = :idPaciente
            """)
    Boolean findActivoById(Long idPaciente);

}
