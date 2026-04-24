package medicore.patientsService.infrastructure.config.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {

    private final KeycloakProperties keycloakProperties;

    public KeycloakClientConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    public Keycloak keycloakAdminClient(){
        return  KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.serverUrl())
                .realm("master")       // admin-cli lives in the master realm
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli") // built-in public client — no secret needed
                .username(keycloakProperties.kcUser())
                .password(keycloakProperties.kcPass())
                .build();
    }

    @Bean
    public RealmResource getReamResource(){
        return keycloakAdminClient().realm(keycloakProperties.realm());
    }
}
