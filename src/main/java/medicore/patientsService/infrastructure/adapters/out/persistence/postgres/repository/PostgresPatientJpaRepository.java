package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.repository;

import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface PostgresPatientJpaRepository extends JpaRepository<PatientEntity,String>, JpaSpecificationExecutor<PatientEntity> {
    Optional<PatientEntity> findByUuid(String uuid);
}
