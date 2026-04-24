package medicore.patientsService.infrastructure.config.usecases;

import medicore.patientsService.application.RegisterPatientUseCaseImpl;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.domain.ports.out.IdentityProviderPort;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatientServiceConfig {

    @Bean
    public RegisterPatientUseCase registerPatientUseCase (PatientRepositoryPort patientRepositoryPort, IdentityProviderPort identityProviderPort){
        return new RegisterPatientUseCaseImpl(patientRepositoryPort,identityProviderPort);
    }
}
