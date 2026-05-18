package medicore.patientsService.infrastructure.adapters.in.web.handlers;

import lombok.extern.slf4j.Slf4j;
import medicore.patientsService.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

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
        log.error(ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error during patient registration, try again later."));
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<?> handleRegistrationFailedException(PatientAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problemDetail(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex){
        List<Map<String,String>> errors = new ArrayList<>();
        for(FieldError e : ex.getFieldErrors()){
            errors.add(Map.of("field",e.getField(),"message",e.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problemDetail(HttpStatus.BAD_REQUEST, "Validation failed for the request payload", errors));
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail(HttpStatus.NOT_FOUND, ex.getMessage()));
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(problemDetail(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    // Jackson error for Unrecognized fields in body request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        log.error("Unidentified fields in the request : {}",ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problemDetail(HttpStatus.BAD_REQUEST, "Unidentified fields in the request"));
    }

    @ExceptionHandler(UpdatePatientFailedException.class)
    public ResponseEntity<?> handleUpdatePatientFailedExceptionException( UpdatePatientFailedException ex){
        log.error("Database operation failed: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail(HttpStatus.INTERNAL_SERVER_ERROR,"The patient's information could not be updated. Please try again later."));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception ex){
        log.error(ex.getMessage(), ex.fillInStackTrace());
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

    private <T> ProblemDetail problemDetail (HttpStatus httpStatus, String message, T errors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("traceId", UUID.randomUUID().toString());
        problemDetail.setProperty("errors",errors);
        return problemDetail;
    }
}
