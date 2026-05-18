package medicore.patientsService.domain.ports.in;


import medicore.patientsService.domain.models.Patient;



public interface GetPatientProfileUseCase {

    Patient getPatientProfile(String uuid);


}
