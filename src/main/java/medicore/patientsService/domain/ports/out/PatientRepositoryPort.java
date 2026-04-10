package medicore.patientsService.domain.ports.out;

import medicore.patientsService.domain.models.Patient;

public interface PatientRepositoryPort {
    void save (Patient patient);
}
