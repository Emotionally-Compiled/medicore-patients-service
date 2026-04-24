package medicore.patientsService.infrastructure.adapters.out.persistence.postgres;

import lombok.AllArgsConstructor;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;
import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.mapper.PatientPersistenceMapper;
import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.repository.PostgresPatientJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PostgresPatientRepositoryAdapter implements PatientRepositoryPort {

    private final PostgresPatientJpaRepository patientRepository;
    private final PatientPersistenceMapper mapper;

    @Override
    public void save(Patient patient)   {
        patientRepository.save(mapper.toEntity(patient));
    }

    @Override
    public Optional<Patient> findByUuid(String uuid) {
        return patientRepository.findByUuid(uuid)
                .map(mapper::toModel);
    }
}
