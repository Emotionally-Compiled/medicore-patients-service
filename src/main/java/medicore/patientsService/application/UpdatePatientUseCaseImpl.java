package medicore.patientsService.application;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.ResourceNotFoundException;
import medicore.patientsService.domain.exceptions.UpdatePatientFailedException;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.UpdatePatientUseCase;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;



@Slf4j
public class UpdatePatientUseCaseImpl implements UpdatePatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;

    public UpdatePatientUseCaseImpl(PatientRepositoryPort patientRepositoryPort) {
        this.patientRepositoryPort = patientRepositoryPort;
    }

    @Override
    public void execute(String uuid, UpdatePatientCommand command) {
        log.info("Patient with UUID {} request a profile update", uuid);

        Patient patient = patientRepositoryPort.findByUuid(uuid) // search patient
                .orElseThrow(() -> new ResourceNotFoundException("Patient Not Found with UUID " + uuid));
        try {

            //set new values
            patient.setFirstName(command.firstName());
            patient.setLastName(command.lastName());
            patient.setPhoneNumber(command.phoneNumber());
            patient.setDateOfBirth(command.dateOfBirth());

            //call repository for update patient
            patientRepositoryPort.save(patient);

        } catch (PersistenceException e) {
            throw new UpdatePatientFailedException("Unexpected error updating patient " + uuid,e);
        }

    }
}
