package medicore.patientsService.domain.models;

import medicore.patientsService.domain.exceptions.InvalidCredentialsException;

import java.time.LocalDate;

public class Patient {
    private  String UUID;
    private  String name;
    private  String lastName;
    private  String phoneNumber;
    private  String email;
    private  LocalDate dateOfBirth;
    private  IdentityDocument identityDocument ;

    public Patient(String UUID, String name, String lastName, String phoneNumber, String email, LocalDate dateOfBirth, IdentityDocument identityDocument){
        this.UUID = UUID;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.identityDocument = identityDocument;
    }

    public Patient(IdentityDocument identityDocument, LocalDate dateOfBirth, String email, String phoneNumber, String lastName, String name) {
        this.identityDocument = identityDocument;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastName = validateCredential(lastName) ;
        this.name = validateCredential(name);
    }

    public Patient( String UUID,String name, String lastName, IdentityDocument identityDocument) {
        this.UUID = UUID;
        this.name = validateCredential(name);
        this.lastName = validateCredential(lastName);
        this.identityDocument = identityDocument;
    }

    public void updateEmail(String email){
        this.email = validateEmail(email);
    }

    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = validatePhoneNumber(phoneNumber);
    }

    public String validateCredential(String value){
        if( value == null || value.trim().isEmpty()){
            throw new InvalidCredentialsException("Patient", Patient.class);
        }
        return value.trim();
    }

    public String validatePhoneNumber(String value){
        //String phone = validateCredential(value);
        if (!value.matches("\\b\\d+\\b")){ // regex only to accept numbers
            throw new InvalidCredentialsException("Phone number : "  + value, Patient.class);
        }
        return value;
    }

    public String validateEmail(String value){
        //String email = validateCredential(value);
        if(!value.matches("^\\\\S+@\\\\S+\\\\.\\\\S+$")){ // regex for email
            throw new InvalidCredentialsException("Email : " + value, Patient.class);
        }
        return value;
    }

    public String getUUID() {
        return UUID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public IdentityDocument getIdentityDocument() {
        return identityDocument;
    }
}
