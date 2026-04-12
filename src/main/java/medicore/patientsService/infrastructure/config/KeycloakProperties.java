package medicore.patientsService.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record KeycloakProperties(
        String issuerUri,
        String clientId,
        String clientSecret,
        String kcUser,
        String kcPass,
        String kcRealm
) { }
