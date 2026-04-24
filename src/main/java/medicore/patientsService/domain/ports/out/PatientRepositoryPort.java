package medicore.patientsService.domain.ports.out;

import medicore.patientsService.domain.models.Patient;

import java.util.Optional;

public interface PatientRepositoryPort {
    void save (Patient patient);
    Optional<Patient> findByUuid(String uuid);
}
