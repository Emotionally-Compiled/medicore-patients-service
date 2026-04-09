package medicore.patientsService.domain.ports.in;

import medicore.patientsService.domain.models.IdentityDocument;

public interface RegisterPatientUseCase {

    void register(RegisterCommand command);

    record RegisterCommand(
            String name,
            String lastName,
            IdentityDocument identityDocument,
            String email
    ){}
}
