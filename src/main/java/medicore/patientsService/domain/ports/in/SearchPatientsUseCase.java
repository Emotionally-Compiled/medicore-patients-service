package medicore.patientsService.domain.ports.in;

import medicore.patientsService.domain.models.FilterSearchPatient;
import medicore.patientsService.domain.models.Page;
import medicore.patientsService.domain.models.Patient;


public interface SearchPatientsUseCase {
    Page<Patient> execute (int size, int page, FilterSearchPatient filter);
}
