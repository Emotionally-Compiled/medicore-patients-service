package medicore.patientsService.infrastructure.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {

    private final KeycloakProperties keycloakProperties;

    public KeycloakClientConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public Keycloak keycloakAdminClient(){
        return  KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.issuerUri())
                .realm("medicore-realm")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakProperties.clientId())
                .clientSecret(keycloakProperties.clientSecret())
                .username(keycloakProperties.kcUser())
                .password(keycloakProperties.kcPass())
                .build();
    }
}
