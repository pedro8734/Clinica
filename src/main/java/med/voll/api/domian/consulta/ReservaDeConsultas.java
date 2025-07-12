package med.voll.api.domian.consulta;


import jakarta.transaction.Transactional;
import med.voll.api.domian.ValidacionException;
import med.voll.api.domian.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domian.consulta.validaciones.ValidadorDeConsulta;
import med.voll.api.domian.medico.Medico;
import med.voll.api.domian.medico.MedicoRepository;
import med.voll.api.domian.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private List<ValidadorDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;


    @Transactional
    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw  new ValidacionException("No existe un paciente con el id informado");
        }
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw  new ValidacionException("No existe un Medico con el id informado");
        }

        //validaciones
        validadores.forEach(v ->v.validar(datos));

        var medico = elegirMedico(datos);
        if( medico == null ){
            throw  new ValidacionException("No existe ese Medico disponible con ese horario");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null){
            throw  new ValidacionException("Es necesario elegir una especialidad cuando no se elige un medico");
        }
        return  medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }
    @Transactional
    public void cancelar(DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
