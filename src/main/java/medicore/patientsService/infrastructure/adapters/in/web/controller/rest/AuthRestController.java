package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.infrastructure.adapters.in.web.dto.request.PatientRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag( name = "Patients service")
public class AuthRestController {

    private final RegisterPatientUseCase registerUseCase;

    @Parameter(content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = PatientRegisterRequest.class),
            examples = @ExampleObject(value =
                    "{\"firstName\": \"Brisbany\" \"lastName\": \"Puerta Herrera\" \"password\": \"pass123\" \"documentIdentity\": \"00000\"}"
            )
    ))
    @PostMapping("/register")
    public ResponseEntity<?> Register(@Valid @RequestBody PatientRegisterRequest request){
        registerUseCase.register(new RegisterPatientUseCase.RegisterCommand(
                request.firstName(),
                request.lastName(),
                request.password(),
                request.documentIdentity()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201
    }

}
