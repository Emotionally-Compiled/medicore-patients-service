package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Get Patient Profile")
public class PatientProfileController {

    private final GetPatientProfileUseCase getPatientProfileUseCase;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found "),
            @ApiResponse(responseCode = "404", description = "Not found - The patient was not found"),
            @ApiResponse(responseCode = "403", description = "User not have enough permission to access this endpoint ")
    })
    @GetMapping("/me")
    @PreAuthorize("hasRole('Patient')")
    public ResponseEntity<?> getPatientProfile(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK)
                .body(getPatientProfileUseCase.getPatientProfile(jwt.getSubject()));
    }
}
