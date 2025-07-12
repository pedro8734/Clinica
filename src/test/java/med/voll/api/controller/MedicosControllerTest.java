package med.voll.api.controller;

import med.voll.api.domian.direccion.DatosDireccion;
import med.voll.api.domian.direccion.Direccion;
import med.voll.api.domian.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class MedicosControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosRegistroMedico> datosRegistroMedicoJson;

    @Autowired
    private JacksonTester<DatosDetallesMedico> datosDetallesMedicoJson;

    @MockBean
    private MedicoRepository medicoRepository;


    @Test
    @DisplayName("Debería retornar código 400 cuando los datos están incompletos")
    void registrarMedicosCaso1() throws Exception {
        var response = mvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Debería devolver http 201 cuando la request reciba un json válido")
    void registrar_escenario2() throws Exception {
        // Arrange
        var datosdireccion = new DatosDireccion("calle 1", "12", "000000", "Bogota", "111111", "camilo", "cali");
        var datosRegistro = new DatosRegistroMedico(
                "Pedro Montes", "pedro@email.com", "3126113456", "123456987", Especialidad.CARDIOLOGIA, datosdireccion
        );

        // Simular la respuesta del repository con el ID seteado manualmente
        when(medicoRepository.save(any())).thenAnswer(invocation -> {
            Medico medico = invocation.getArgument(0);

            Field idField = Medico.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(medico, 1L); // Inyectar el id manualmente

            return medico;
        });

        // Act
        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroMedicoJson.write(datosRegistro).getJson()))
                .andReturn().getResponse();
        var direccionEsperada = new Direccion("calle 1", "12", "000000", "Bogota", "111111", "camilo", "cali");
        // Creamos el objeto esperado
        var jsonEsperado = datosDetallesMedicoJson.write(
                new DatosDetallesMedico(
                        1L,
                        "Pedro Montes",
                        "pedro@email.com",
                        "3126113456",
                        "123456987",
                        Especialidad.CARDIOLOGIA,
                       direccionEsperada
                )
        ).getJson();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}
