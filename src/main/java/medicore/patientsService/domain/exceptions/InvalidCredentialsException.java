package medicore.patientsService.domain.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public <T> InvalidCredentialsException(String value, Class<T> from ) {
        super("Invalid credentials in : " + value + " | " + from  );
    }
}
