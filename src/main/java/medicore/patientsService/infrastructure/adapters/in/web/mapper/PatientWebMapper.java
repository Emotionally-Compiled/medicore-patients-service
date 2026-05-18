package medicore.patientsService.infrastructure.adapters.in.web.mapper;


import medicore.patientsService.domain.models.FilterSearchPatient;
import medicore.patientsService.domain.models.IdentityDocument;
import medicore.patientsService.domain.models.Page;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.infrastructure.adapters.in.web.dto.reponse.PageResponse;
import medicore.patientsService.infrastructure.adapters.in.web.dto.reponse.PatientResponse;
import medicore.patientsService.infrastructure.adapters.in.web.dto.reponse.PatientSummaryResponse;
import medicore.patientsService.infrastructure.adapters.in.web.dto.query.SearchFilterPatientQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientWebMapper {

    //filter request
    FilterSearchPatient toDomain(SearchFilterPatientQuery request);

    //detail response
    @Mapping(target = "identityDocument", source = "identityDocument.value")
    PatientResponse toDto (Patient patient);


    @Mapping(target = "identityDocument", source = "identityDocument.value")
    PatientSummaryResponse toSummaryResponse( Patient patient);
    List<PatientSummaryResponse> toSummaryResponseList ( List<Patient> patientList);


    default PageResponse<PatientSummaryResponse> toPageResponse(Page<Patient> page) {
        if (page == null) {
            return null;
        }

        List<PatientSummaryResponse> content = toSummaryResponseList(page.content());

        return new PageResponse<>(
                content,
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }

    default IdentityDocument map(String value) {
        return value == null ? null : new IdentityDocument(value);
    }
}
