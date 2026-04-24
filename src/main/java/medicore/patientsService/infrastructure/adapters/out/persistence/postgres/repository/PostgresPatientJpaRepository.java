package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.repository;

import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostgresPatientJpaRepository extends JpaRepository<PatientEntity,String> {
    Optional<PatientEntity> findByUuid(String uuid);
}
