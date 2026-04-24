package medicore.patientsService.application;

import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.ResourceNotFoundException;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.GetPatientProfileUseCase;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
public class GetPatientProfileUseCaseImpl implements GetPatientProfileUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public GetPatientProfileUseCaseImpl(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public GetPatientResponseCommand getPatientProfile(String uuid) {

        log.info("System/database searching for patient with uuid {}", uuid);
        Optional<Patient> patient = patientRepositoryPort.findByUuid(uuid);

        if (patient.isEmpty()){
            throw new ResourceNotFoundException("Patient with uuid " + uuid + "Not Found");
        }
        Patient patientFound = patient.get();

        log.info("Patient ({}) Found!", uuid);
        return new GetPatientResponseCommand(
                patientFound.getFirstName(),
                patientFound.getLastName(),
                patientFound.getPhoneNumber(),
                patientFound.getEmail(),
                patientFound.getDateOfBirth(),
                patientFound.getIdentityDocument().value()
        );
    }
}
