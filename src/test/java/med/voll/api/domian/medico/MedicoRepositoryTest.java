package med.voll.api.domian.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domian.consulta.Consulta;
import med.voll.api.domian.direccion.DatosDireccion;
import med.voll.api.domian.paciente.DatosRegistroPaciente;
import med.voll.api.domian.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Deberia de devolver null cuando el medico buscado existe pero no esta disponible en esa fecha ")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario1() {
        //given o arrange
         var miercoleSiguenteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(10 , 0);
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,miercoleSiguenteAlas10);
         var medico=registrarMedico("medico1","pedro-mo@gmail.com","1069123432",Especialidad.CARDIOLOGIA);
         var paciente=registrarPaciente("paciente1","paciente@gmail.com","1234567890");
         //when o act
         registrarConsulta(medico, paciente,miercoleSiguenteAlas10);

          // then o assert
        assertThat(medicoLibre).isNull();
    }
    @Test
    @DisplayName("Deberia de devolver medico cuando el medico buscado esta disponible  en esa fecha ")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario2() {
        var miercoleSiguenteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(10 , 0);

        var medico=registrarMedico("medico1","pedro-mo@gmail.com","1069123432",Especialidad.CARDIOLOGIA);

        // asegura que la inserción sea visible en la consulta
        em.flush();
        em.clear();
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA,miercoleSiguenteAlas10);

        assertThat(medicoLibre).isEqualTo(medico);
    }
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        Consulta consulta = new Consulta(null, medico, paciente, fecha, null);
        em.persist(consulta);
    }
    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        Medico medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }
    private Paciente registrarPaciente(String nombre, String email, String documento) {
        Paciente paciente = new Paciente(datosPaciente(nombre, email,documento));
        em.persist(paciente);
        return paciente;
    }
    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "3214567890",
                documento,
                especialidad,
                datosDireccion()
        );
    }
    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "3294567890",
                documento,
                datosDireccion()
        );
    }
    private DatosDireccion datosDireccion(){
        return  new DatosDireccion(
          "calle x",
                "5",
                "complemento bcc",
                "las Mericas",
                "123456",
                "bolivar",
                "cordoba"



        );
    }


}