package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

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
public class AuthRestController {

    private final RegisterPatientUseCase registerUseCase;


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
