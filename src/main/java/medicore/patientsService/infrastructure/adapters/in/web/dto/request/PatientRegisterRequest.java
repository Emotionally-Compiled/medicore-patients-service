package medicore.patientsService.infrastructure.adapters.in.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientRegisterRequest(
        @NotBlank(message = "The name is mandatory")
        String firstName,
        @NotBlank(message = "The last name is mandatory")
        String lastName,
        @NotBlank(message = "The document identity is mandatory")
        String documentIdentity,
        @NotBlank(message = "The password is mandatory")
        @Size(min = 6, message = "The password must be greater than 5 characters")
        String password
) {}
