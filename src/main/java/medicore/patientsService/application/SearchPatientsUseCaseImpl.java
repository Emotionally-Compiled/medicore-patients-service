package medicore.patientsService.application;

import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.models.FilterSearchPatient;
import medicore.patientsService.domain.models.Page;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.SearchPatientsUseCase;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class SearchPatientsUseCaseImpl implements SearchPatientsUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public SearchPatientsUseCaseImpl(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Patient> execute(int size, int page, FilterSearchPatient filter) {
        List<Patient> patients = patientRepositoryPort.findAll(filter);


        // build pageable
        int totalPages = patients.size() / size;
        boolean isLast = page == totalPages;

        return new Page<>(patients,page,size, patients.size(),totalPages, isLast);
    }
}
