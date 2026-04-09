package medicore.patientsService.domain.exceptions;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String message) {
        super(message);
    }

    public <T> InvalidCredentials(String value, Class<T> from ) {
        super("Invalid credentials in : " + value + " | " + from  );
    }
}
