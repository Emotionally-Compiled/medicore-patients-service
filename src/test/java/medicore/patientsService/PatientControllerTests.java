package medicore.patientsService;

import com.fasterxml.jackson.databind.ObjectMapper;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.infrastructure.adapters.in.web.dto.request.PatientRegisterRequest;
import medicore.patientsService.infrastructure.config.security.JwtAuthenticationConverter;
import medicore.patientsService.infrastructure.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@WebMvcTest
@Import({SecurityConfig.class, JwtAuthenticationConverter.class})
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private RegisterPatientUseCase registerPatientUseCase;

    @Test // return status code 201
    public void createPatientShouldReturnStatusCodeCreated() throws Exception {

        PatientRegisterRequest patient = PatientRegisterRequest.builder()
                .firstName("Test")
                .lastName("testini")
                .password("test123")
                .documentIdentity("1010")
                .build();

        String json = objectMapper.writeValueAsString(patient);
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
