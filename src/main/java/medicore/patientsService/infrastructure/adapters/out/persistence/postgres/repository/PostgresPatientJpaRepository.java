package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.repository;

import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresPatientJpaRepository extends JpaRepository<PatientEntity,String> {
}
