package com.example.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PhysicalTherapyClinicImp {
    private String name;
    private String address;
    private final int afm;
    private final Set<TherapyService> therapyServiceSet = new HashSet<>();
    private final Doctor doctor;

    public PhysicalTherapyClinicImp(String name, String address, int afm) {
        this.name = name;
        this.address = address;
        this.afm = afm;
        this.doctor = new Doctor();
    }

    public void addTherapyService(TherapyService therapyService){
        therapyServiceSet.add(therapyService);
    }

    public Set<TherapyService> getTherapyServiceSet(){
        return Collections.unmodifiableSet(therapyServiceSet);
    }

    public Doctor getDoctor(){
        return doctor;
    }
}
