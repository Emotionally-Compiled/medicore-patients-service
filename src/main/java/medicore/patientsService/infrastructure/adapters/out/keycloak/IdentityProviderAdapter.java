package medicore.patientsService.infrastructure.adapters.out.keycloak;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import medicore.patientsService.domain.exceptions.PatientAlreadyExistsException;
import medicore.patientsService.domain.exceptions.ResourceNotFoundException;
import medicore.patientsService.domain.ports.out.IdentityProviderPort;

import medicore.patientsService.infrastructure.config.keycloak.KeycloakProperties;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
@AllArgsConstructor
public class IdentityProviderAdapter implements IdentityProviderPort {

    private final RealmResource realmResource;


    @Override
    public String registerPatient(String identityDocument, String password, String firstName, String lastName) {

        // check if the user already exists
        List<UserRepresentation> existsPatient = realmResource.users().search(identityDocument,true);

        if(!existsPatient.isEmpty()){
            throw new PatientAlreadyExistsException("Patient "+ identityDocument + " already exists");
        }


        UserRepresentation patient = new UserRepresentation();
        CredentialRepresentation passwordCred = new CredentialRepresentation();

        // define patient data
        patient.setEnabled(true);
        patient.setUsername(identityDocument);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);


        // define patient credential (password)
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setTemporary(false);
        passwordCred.setValue(password);
        patient.setCredentials(List.of(passwordCred));


        //create user
        Response response = realmResource.users().create(patient);
        // save patient id created by keycloak
        String patientId = CreatedResponseUtil.getCreatedId(response);



        // search role from keycloak realm
        RoleRepresentation realmRole = realmResource
                .roles()
                .get("Patient")
                .toRepresentation();

        // Set roles to the new patient
       realmResource.users().get(patientId)
                .roles()
                .realmLevel()
                .add(Collections.singletonList(realmRole));



        return patientId;
    }

    @Override
    public void deleteUser(String authId) {
       realmResource.users().get(authId).remove();
    }

    @Override
    public void updateEmail(String email, String authId) {
        //get patient from keycloak
        UserRepresentation patient = realmResource.users().get(authId).toRepresentation();

        //validate id user exists
        if(patient == null){
            throw new ResourceNotFoundException("Patient with authId " + authId + " Not found in keycloak data");
        }

        patient.setEmail(email); // update email
        realmResource.users().get(authId).update(patient); // send the patient updated to keycloak
    }
}
