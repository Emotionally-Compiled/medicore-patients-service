package medicore.patientsService.application;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.RegistrationFailedException;
import medicore.patientsService.domain.models.IdentityDocument;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.domain.ports.out.IdentityProviderPort;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
public class RegisterPatientUseCaseImpl implements RegisterPatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;
    private final IdentityProviderPort identityProviderPort;


    public RegisterPatientUseCaseImpl(PatientRepositoryPort patientRepositoryPort,
                                      IdentityProviderPort identityProviderPort) {
        this.patientRepositoryPort = patientRepositoryPort;
        this.identityProviderPort = identityProviderPort;
    }

    @Override
    @Transactional
    public void register(RegisterCommand command) {

        log.info("Start patient registration process for Identity Document: {}", command.identityDocument());

        IdentityDocument newID = new IdentityDocument(command.identityDocument()); //Create IdentityDocument object
        String authId = null;

        try {
            /*
                Call external IAM (Identity and Access Management)
            */
            authId = identityProviderPort.registerPatient(newID.value(), command.password(), command.name(), command.lastName());
            log.info("Identity Provider successfully registered and return UUID ({}) for new patient", authId);

            Patient newPatient = new Patient(
                    authId,
                    command.name(),
                    command.lastName(),
                    newID
            );

            //save patient in 'patients_db' database
            patientRepositoryPort.save(newPatient);
            log.info("Patient registered successfully UUID {} ", authId);


        } catch (PersistenceException e){ // catch errors from database
            log.error("Unexpected error during saving patient from database for document: {}. Reason: {}",
                    command.identityDocument(),
                    e.getMessage()
            );
            identityProviderPort.deleteUser(authId);
            log.info("Patient with uuid {} deleted from identity provider database ", authId);
            throw new RegistrationFailedException("Error saving patient from database ",e);
        }

        catch (Exception e) { // catch unexpected errors

            log.error("Unexpected error during patient registration for document: {}. Reason: {}",
                    command.identityDocument(),
                    e.getMessage()
            );

            throw new RegistrationFailedException("Internal Error during Register", e);
        }


    }
}
