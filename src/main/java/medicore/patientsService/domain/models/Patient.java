package medicore.patientsService.domain.models;

import medicore.patientsService.domain.exceptions.InvalidCredentialsException;

import java.time.LocalDate;

public class Patient {
    private  String uuid;
    private  String firstName;
    private  String lastName;
    private  String phoneNumber;
    private  String email;
    private  LocalDate dateOfBirth;
    private  IdentityDocument identityDocument ;

    public Patient(String uuid, String firstName, String lastName, String phoneNumber, String email, LocalDate dateOfBirth, IdentityDocument identityDocument){
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.identityDocument = identityDocument;
    }

    public Patient(IdentityDocument identityDocument, LocalDate dateOfBirth, String email, String phoneNumber, String lastName, String firstName) {
        this.identityDocument = identityDocument;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastName = validateCredential(lastName) ;
        this.firstName = validateCredential(firstName);
    }

    public Patient( String uuid,String firstName, String lastName, IdentityDocument identityDocument) {
        this.uuid = uuid;
        this.firstName = validateCredential(firstName);
        this.lastName = validateCredential(lastName);
        this.identityDocument = identityDocument;
    }

    public Patient() {
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

    public String getuuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFirstName(String firstName) {
        this.firstName = validateCredential(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = validateCredential(lastName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = validatePhoneNumber(phoneNumber);
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setIdentityDocument(IdentityDocument identityDocument) {
        this.identityDocument = identityDocument;
    }
}
