package medicore.patientsService.domain.ports.in;

import java.time.LocalDate;

public interface UpdatePatientUseCase {

    void execute(String uuid, UpdatePatientCommand command);

    record UpdatePatientCommand (
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate dateOfBirth
    ){}

}
