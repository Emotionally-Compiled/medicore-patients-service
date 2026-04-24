package medicore.patientsService.domain.ports.in;


import java.time.LocalDate;

public interface GetPatientProfileUseCase {

    GetPatientResponseCommand getPatientProfile(String uuid);

    record GetPatientResponseCommand (
         String firstName,
         String lastName,
         String phoneNumber,
         String email,
         LocalDate dateOfBirth,
         String identityDocument
    ){}
}
