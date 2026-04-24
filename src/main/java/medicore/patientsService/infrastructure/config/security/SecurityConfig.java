package medicore.patientsService.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import medicore.patientsService.infrastructure.config.keycloak.KeycloakProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // for roles
@EnableWebSecurity
@EnableConfigurationProperties(KeycloakProperties.class)
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtAuthenticationConverter jwtAuthenticationConverter;


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        //swagger
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/error").permitAll()

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        oauth -> oauth.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }




}
