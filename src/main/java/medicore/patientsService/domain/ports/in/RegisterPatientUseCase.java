package medicore.patientsService.domain.ports.in;


public interface RegisterPatientUseCase {

    void register(RegisterCommand command);

    record RegisterCommand(
            String name,
            String lastName,
            String password,
            String identityDocument
    ){}
}
