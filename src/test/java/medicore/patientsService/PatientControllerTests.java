package medicore.patientsService;

import com.fasterxml.jackson.databind.ObjectMapper;
import medicore.patientsService.domain.models.IdentityDocument;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.domain.ports.in.SearchPatientsUseCase;
import medicore.patientsService.domain.ports.in.UpdatePatientUseCase;
import medicore.patientsService.infrastructure.adapters.in.web.dto.reponse.PatientResponse;
import medicore.patientsService.infrastructure.adapters.in.web.dto.request.PatientRegisterRequest;
import medicore.patientsService.infrastructure.adapters.in.web.dto.request.UpdatePatientRequest;
import medicore.patientsService.infrastructure.adapters.in.web.mapper.PatientWebMapper;
import medicore.patientsService.infrastructure.config.security.JwtAuthenticationConverter;
import medicore.patientsService.infrastructure.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



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

    @MockitoBean
    private GetPatientProfileUseCase getPatientProfileUseCase;

    @MockitoBean
    private UpdatePatientUseCase updatePatientUseCase;

    @MockitoBean
    private SearchPatientsUseCase searchPatientsUseCase;

    @MockitoBean
    private PatientWebMapper patientWebMapper;

    private final Patient mockResponse = new Patient(
            new IdentityDocument("100011100"),
            LocalDate.now(),
            "test@example.com",
            "32020202",
            "testini",
            "test"

    );



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
                .andExpect(status().isCreated());
    }

    @Test // return status code 400
    public void createPatientShouldReturnStatusCodeBadRequest() throws Exception {

        PatientRegisterRequest patient = PatientRegisterRequest.builder()
                .firstName("")
                .lastName("testini")
                .password("test123")
                .documentIdentity("1010")
                .build();

        String json = objectMapper.writeValueAsString(patient);
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test // 200
    public void GetCurrentPatientShouldReturnStatusCodeOk() throws Exception {
        String mockSubject = "user-uuid-12345";

        when(getPatientProfileUseCase.getPatientProfile(anyString())).thenReturn(mockResponse);
        when(patientWebMapper.toDto(any(Patient.class))).thenReturn(
                new PatientResponse("testini", "test", "32020202", "test@example.com", LocalDate.now(), "100011100"));

        mockMvc.perform(get("/me")
                        .with(csrf())
                        .with(jwt()
                                .jwt(builder -> builder.subject(mockSubject))
                                .authorities(new SimpleGrantedAuthority("ROLE_Patient"))

                        ).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

    }

    @Test // 403
    public void GetCurrentPatientShouldReturnStatusCodeForbidden() throws Exception {
        String mockSubject = "user-uuid-12345";

        when(getPatientProfileUseCase.getPatientProfile(anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/me")
                        .with(csrf())
                        .with(jwt()
                                .jwt(builder -> builder.subject(mockSubject))
                                .authorities(new SimpleGrantedAuthority("ROLE_Doctor")) // change role

                        ).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }


    @Test
    public void UpdateCurrentPatientShouldReturnOk() throws Exception {
        String mockSubject = "user-uuid-12345";

        UpdatePatientRequest request = new UpdatePatientRequest(
                "Test",
                "testini",
                "10101010",
                LocalDate.now());

        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/me")
                        .with(csrf())
                        .with(jwt()
                                .jwt(builder -> builder.subject(mockSubject))
                                .authorities(new SimpleGrantedAuthority("ROLE_Patient")) // change role

                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk());
    }


    @Test
    public void UpdateCurrentPatientShouldReturnBadRequest() throws Exception {
        String mockSubject = "user-uuid-12345";

        UpdatePatientRequest request = new UpdatePatientRequest(
                "",
                "testini",
                "aaaaa",
                LocalDate.now());

        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/me")
                        .with(csrf())
                        .with(jwt()
                                .jwt(builder -> builder.subject(mockSubject))
                                .authorities(new SimpleGrantedAuthority("ROLE_Patient")) // change role

                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest());

    }
}
