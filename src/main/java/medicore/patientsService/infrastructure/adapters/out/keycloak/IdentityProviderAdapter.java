package medicore.patientsService.infrastructure.adapters.out.keycloak;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import medicore.patientsService.domain.ports.out.IdentityProviderPort;

import medicore.patientsService.infrastructure.config.KeycloakProperties;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@AllArgsConstructor
public class IdentityProviderAdapter implements IdentityProviderPort {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;


    @Override
    public String registerPatient(String identityDocument, String password, String firstName, String lastName) {

        UserRepresentation patient = new UserRepresentation();
        CredentialRepresentation passwordCred = new CredentialRepresentation();

        // define patient data
        patient.setEnabled(true);
        patient.setUsername(identityDocument);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);


        //create user
        Response response = keycloak.realm(keycloakProperties.kcRealm()).users().create(patient);

        // save patient id created by keycloak
        String patientId = CreatedResponseUtil.getCreatedId(response);

        // define patient credential (password)
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);


        //change temporary credential to the password (no temporary)
        keycloak.realm(keycloakProperties.kcRealm()).users().get(patientId).resetPassword(passwordCred);

        // search role from keycloak realm
        RoleRepresentation realmRole = keycloak.realm(keycloakProperties.kcRealm())
                .roles()
                .get("Patient")
                .toRepresentation();

        // Set roles to the new patient
        keycloak.realm(keycloakProperties.kcRealm()).users().get(patientId)
                .roles()
                .realmLevel()
                .add(Collections.singletonList(realmRole));


        return patientId;
    }

    @Override
    public void deleteUser(String authId) {
        keycloak.realm(keycloakProperties.kcRealm()).users().get(authId).remove();
    }

    @Override
    public void updateEmail(String email, String authId) {

        //get patient from keycloak
        UserRepresentation patient = keycloak.realm(keycloakProperties.kcRealm()).users().get(authId).toRepresentation();
        patient.setEmail(email); // update email
        keycloak.realm(keycloakProperties.kcRealm()).users().get(authId).update(patient); // send the patient updated to keycloak
    }
}
