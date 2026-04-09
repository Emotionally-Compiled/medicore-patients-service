package medicore.patientsService.domain.models;

import medicore.patientsService.domain.exceptions.InvalidCredentials;

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
        this.email = validateEmail(email);
        this.phoneNumber = validatePhoneNumber(phoneNumber);
        this.lastName = validateCredential(lastName) ;
        this.name = validateCredential(name);
    }


    public void updateEmail(String email){
        this.email = email;
    }

    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String validateCredential(String value){
        if( value == null || value.trim().isEmpty()){
            throw new InvalidCredentials("Patient", Patient.class);
        }
        return value.trim();
    }

    public String validatePhoneNumber(String value){
        String phone = validateCredential(value);
        if (!phone.matches("\\b\\d+\\b")){
            throw new InvalidCredentials("Phone number : "  + phone, Patient.class);
        }
        return phone;
    }

    public String validateEmail(String value){
        String email = validateCredential(value);
        if(!email.matches("^\\\\S+@\\\\S+\\\\.\\\\S+$")){
            throw new InvalidCredentials("Email : " + email, Patient.class);
        }
        return email;
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
