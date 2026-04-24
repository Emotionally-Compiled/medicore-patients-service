package medicore.patientsService.domain.ports.out;


public interface IdentityProviderPort {

    /**
     * Register a patient
     *
     * @param identityDocument the identification number of the patient
     * @param password the password
     * @param firstName
     * @param lastName
     * @return the UUID in String format
     */
    String registerPatient(String identityDocument, String password, String firstName, String lastName);

    void deleteUser(String authId);

    void updateEmail(String email, String authId);
}
