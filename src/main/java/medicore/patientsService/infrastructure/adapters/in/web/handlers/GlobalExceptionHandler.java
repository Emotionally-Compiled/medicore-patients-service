package medicore.patientsService.infrastructure.adapters.in.web.handlers;

import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.InvalidCredentialsException;
import medicore.patientsService.domain.exceptions.PatientAlreadyExistsException;
import medicore.patientsService.domain.exceptions.RegistrationFailedException;
import medicore.patientsService.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredential(InvalidCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problemDetail(HttpStatus.BAD_REQUEST, ex.getMessage())) ;
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<?> handleRegistrationFailedException(RegistrationFailedException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<?> handleRegistrationFailedException(PatientAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problemDetail(HttpStatus.CONFLICT, ex.getMessage()));
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Internal error in patient microservice"));
    }



    private ProblemDetail problemDetail (HttpStatus httpStatus, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        return problemDetail;
    }
}
