package medicore.patientsService.domain.models;

import medicore.patientsService.domain.exceptions.InvalidCredentials;

public class IdentityDocument {
    private final String value;
    /*
    Just for MVP.
    Later it will be an ENUM class named 'DocumentType' with a switch case with different validation depending on the type of id
  */

    public IdentityDocument(String value) {
        if(value == null || value.trim().isEmpty()){
           throw new InvalidCredentials("Identity Document", IdentityDocument.class);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
