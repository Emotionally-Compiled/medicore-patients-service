package medicore.patientsService.domain.models;

import medicore.patientsService.domain.exceptions.InvalidCredentialsException;

public record IdentityDocument(String value) {
    /*
    Just for MVP.
    Later create an ENUM class named 'DocumentType', Then with a switch case with different validation depending on the type of id
  */

    public IdentityDocument {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidCredentialsException("Identity Document", IdentityDocument.class);
        }
    }
}
