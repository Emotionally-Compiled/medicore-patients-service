package medicore.patientsService.infrastructure.adapters.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdatePatientRequest(
        @NotBlank(message = "The name is mandatory")
        String firstName,
        @NotBlank(message = "The last name is mandatory")
        String lastName,
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Incorrect format phone number")
        String phoneNumber,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dateOfBirth
) {
}
