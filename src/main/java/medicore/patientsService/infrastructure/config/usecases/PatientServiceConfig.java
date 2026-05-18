package medicore.patientsService.infrastructure.config.usecases;

import medicore.patientsService.application.GetPatientProfileUseCaseImpl;
import medicore.patientsService.application.RegisterPatientUseCaseImpl;
import medicore.patientsService.application.SearchPatientsUseCaseImpl;
import medicore.patientsService.application.UpdatePatientUseCaseImpl;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.domain.ports.in.SearchPatientsUseCase;
import medicore.patientsService.domain.ports.in.UpdatePatientUseCase;
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

    @Bean
    public GetPatientProfileUseCase getPatientProfileUseCase(PatientRepositoryPort patientRepositoryPort){
        return new GetPatientProfileUseCaseImpl(patientRepositoryPort);
    }

    @Bean
    public UpdatePatientUseCase updatePatientUseCase(PatientRepositoryPort patientRepositoryPort){
        return new UpdatePatientUseCaseImpl(patientRepositoryPort);
    }

    @Bean
    public SearchPatientsUseCase searchPatientsUseCase(PatientRepositoryPort patientRepositoryPort){
        return new SearchPatientsUseCaseImpl(patientRepositoryPort);
    }
}
