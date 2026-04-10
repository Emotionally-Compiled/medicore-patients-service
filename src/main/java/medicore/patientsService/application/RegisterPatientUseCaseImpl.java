package medicore.patientsService.application;

import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.RegistrationFailedException;
import medicore.patientsService.domain.models.IdentityDocument;
import medicore.patientsService.domain.models.Patient;
import medicore.patientsService.domain.ports.in.RegisterPatientUseCase;
import medicore.patientsService.domain.ports.out.IdentityProvider;
import medicore.patientsService.domain.ports.out.PatientRepositoryPort;


@Slf4j

public class RegisterPatientUseCaseImpl implements RegisterPatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;
    private final IdentityProvider identityProvider;




    public RegisterPatientUseCaseImpl(PatientRepositoryPort patientRepositoryPort,
                                      IdentityProvider identityProvider) {
        this.patientRepositoryPort = patientRepositoryPort;
        this.identityProvider = identityProvider;
    }

    @Override
    public void register(RegisterCommand command) {

        log.info("Start patient registration process for Identity Document: {}", command.identityDocument());

        /*Create IdentityDocument and patient object*/
        IdentityDocument newID = new IdentityDocument(command.identityDocument());
        try{
            /*
                Call external IAM (Identity and Access Management)
            */
            String UUID = identityProvider.registerPatient(newID.value(), command.password());
            log.info("Identity Provider successfully registered and return UUID ({}) for new patient", UUID);

            Patient newPatient = new Patient(
                    UUID,
                    command.name(),
                    command.lastName(),
                    newID
            );

            //save patient in patients database
           patientRepositoryPort.save(newPatient);
           log.info("Patient registered successfully UUID {} ", UUID);

        } catch (Exception e) { // catch unexpected errors

            log.error("Unexpected error during patient registration for document: {}. Reason: {}",
                    command.identityDocument(),
                    e.getMessage()
            );


            throw new RegistrationFailedException("Internal Error during Register", e);
        }


    }
}
