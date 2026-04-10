package medicore.patientsService.domain.ports.out;


public interface IdentityProvider {

    /**
     * Register a patient
     *
     * @param identityDocument the identification number of the patient
     * @param password the password
     * @return the UUID in String format
     */
    String registerPatient(String identityDocument, String password);

}
