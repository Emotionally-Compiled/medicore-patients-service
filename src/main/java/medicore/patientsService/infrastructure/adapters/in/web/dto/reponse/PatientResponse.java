package medicore.patientsService.infrastructure.adapters.in.web.dto.reponse;

import java.time.LocalDate;

public record PatientResponse(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        LocalDate dateOfBirth,
        String identityDocument
) { }
