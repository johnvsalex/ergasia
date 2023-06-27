package com.example.domain;

public class PSFUser {

    public PhysicalTherapyClinic createPhysicalTherapyClinic(String name, String address, int afm){
        return new PhysicalTherapyClinic(name, address, afm);
    }

    public TherapyService createTherapyService(int code, String name, String description, float cost){
        return new TherapyService(code, name, description, cost);
    }
	
	public void addTherapyServiceToClinic(TherapyService therapyService, @NonNull PhysicalTherapyClinic clinic){
        clinic.addTherapyService(therapyService);
    }
}
