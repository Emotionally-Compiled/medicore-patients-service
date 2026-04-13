package medicore.patientsService.infrastructure.adapters.out.persistence.postgres.mapper;


import medicore.patientsService.domain.models.IdentityDocument;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.infrastructure.adapters.out.persistence.postgres.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" )
public interface PatientPersistenceMapper {

   Patient toModel(PatientEntity patient);

   @Mapping(target = "identityDocument" ,source = "identityDocument.value")
   PatientEntity toEntity(Patient patient);


   default IdentityDocument map(String value) {
      return value == null ? null : new IdentityDocument(value);
   }
}
