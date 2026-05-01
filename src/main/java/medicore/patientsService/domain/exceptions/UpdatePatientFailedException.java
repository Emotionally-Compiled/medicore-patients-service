package medicore.patientsService.domain.exceptions;

import jakarta.persistence.PersistenceException;

public class UpdatePatientFailedException extends RuntimeException {
    public UpdatePatientFailedException(String message) {
        super(message);
    }

    public UpdatePatientFailedException() {
        super("Unexpected error updating patient");
    }

    public UpdatePatientFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
