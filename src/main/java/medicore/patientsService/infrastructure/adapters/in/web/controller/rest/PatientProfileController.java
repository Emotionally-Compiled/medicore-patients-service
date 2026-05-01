package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import medicore.patientsService.domain.ports.in.UpdatePatientUseCase;
import medicore.patientsService.infrastructure.adapters.in.web.dto.request.UpdatePatientRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Management Patient Profile")
@RequestMapping("/me")
public class PatientProfileController {

    private final GetPatientProfileUseCase getPatientProfileUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found "),
            @ApiResponse(responseCode = "404", description = "Not found - The patient was not found"),
            @ApiResponse(responseCode = "403", description = "User not have enough permission to access this endpoint ")
    })

    @PreAuthorize("hasRole('Patient')")
    @GetMapping
    public ResponseEntity<?> getPatientProfile(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK)
                .body(getPatientProfileUseCase.getPatientProfile(jwt.getSubject()));
    }
    

    @PreAuthorize("hasRole('Patient')")
    @PutMapping
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UpdatePatientRequest request){
        updatePatientUseCase.execute(jwt.getSubject(),
                new UpdatePatientUseCase.UpdatePatientCommand(
                        request.firstName(),
                        request.lastName(),
                        request.phoneNumber(),
                        request.dateOfBirth()
                ));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
