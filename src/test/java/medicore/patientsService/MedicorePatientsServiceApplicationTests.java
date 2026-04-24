package medicore.patientsService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
@ActiveProfiles("test")
class MedicorePatientsServiceApplicationTests {


    @MockitoBean
    private org.keycloak.admin.client.Keycloak keycloakClient;


    @MockitoBean
    private org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;

    @Test
    void contextLoads() {
    }

}
