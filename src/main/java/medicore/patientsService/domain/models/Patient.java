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
        this.lastName = lastName ;
        this.firstName = firstName;
    }

    public Patient( String uuid,String firstName, String lastName, IdentityDocument identityDocument) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityDocument = identityDocument;
    }

    public Patient() {
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
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setIdentityDocument(IdentityDocument identityDocument) {
        this.identityDocument = identityDocument;
    }
}
