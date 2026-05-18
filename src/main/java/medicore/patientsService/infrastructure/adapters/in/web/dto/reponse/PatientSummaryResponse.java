package medicore.patientsService.infrastructure.adapters.in.web.dto.reponse;


public record PatientSummaryResponse (
        String firstName,
        String lastName,
        String identityDocument) {

}
