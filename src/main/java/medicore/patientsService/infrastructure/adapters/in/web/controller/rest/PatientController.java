package medicore.patientsService.infrastructure.adapters.in.web.controller.rest;

import lombok.RequiredArgsConstructor;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import medicore.patientsService.domain.ports.in.SearchPatientsUseCase;
import medicore.patientsService.infrastructure.adapters.in.web.dto.reponse.PageResponse;
import medicore.patientsService.infrastructure.adapters.in.web.dto.query.SearchFilterPatientQuery;
import medicore.patientsService.infrastructure.adapters.in.web.mapper.PatientWebMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patients")
@RequiredArgsConstructor
public class PatientController {

    private final SearchPatientsUseCase searchPatients;
    private final GetPatientProfileUseCase getProfile;
    private final PatientWebMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('Doctor', 'Admin')")
    public ResponseEntity<?> searchPatients(@ModelAttribute SearchFilterPatientQuery request,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "0") int page
    ){

        PageResponse<?> pageResponse = mapper
                .toPageResponse(searchPatients.execute(size,page,mapper.toDomain(request)));

        return ResponseEntity.ok(pageResponse);
    }

    @PreAuthorize("hasAnyRole('Doctor', 'Admin')")
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPatientProfile(@PathVariable String uuid){
        return ResponseEntity.ok(mapper.toDto( getProfile.getPatientProfile(uuid)));
    }


}
