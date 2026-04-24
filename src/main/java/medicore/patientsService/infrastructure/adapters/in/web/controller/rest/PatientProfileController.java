package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

import lombok.RequiredArgsConstructor;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientProfileController {

    private final GetPatientProfileUseCase getPatientProfileUseCase;

    @GetMapping("/me")
    public ResponseEntity<?> getPatientProfile(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(getPatientProfileUseCase.getPatientProfile(jwt.getSubject()));
    }
}
