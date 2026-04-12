package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.mapper;


import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientPersistenceMapper {

   PatientEntity toEntity(Patient patient);
}
