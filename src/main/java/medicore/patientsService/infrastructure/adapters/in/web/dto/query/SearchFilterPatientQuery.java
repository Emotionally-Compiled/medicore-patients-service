package medicore.patientsService.infrastructure.adapters.in.web.dto.query;

public record SearchFilterPatientQuery(
        String name,
        String identityDocument
) {
}
