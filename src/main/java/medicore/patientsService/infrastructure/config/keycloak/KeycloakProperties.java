package medicore.patientsService.infrastructure.config.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kc")
public record KeycloakProperties(
        String serverUrl,
        String clientId,
        String clientSecret,
        String kcUser,
        String kcPass,
        String realm,
        String principleAttribute
) { }
