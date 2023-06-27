package com.example.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Patient {
    private String name;
    private String address;
    private final int amka;
	private final Set<Request> requestSet = new HashSet<>();
    private final List<Float> economicMovements = new ArrayList<>();

    public Patient(int amka){
        this.amka = amka;
    }

    public Patient(String name, String address, int amka) {
        this(amka);
        this.name = name;
        this.address = address;
    }

    public void appointmentRequest(@NonNull Doctor doctor, TherapyService therapyService){
        Request request = new Request(Description.ADD_APPOINTMENT, this, therapyService);
        doctor.manageRequests(request);
        requestSet.add(request);
    }

    public List<Float> showEconomicMovements(){
        return Collections.unmodifiableList(economicMovements);
    }
	
    public void withdraw(Float cost) {
        economicMovements.add(-cost);
    }


    public void remove(Request request) {
        requestSet.remove(request);
    }

    public int getAmka() {
        return amka;
    }

    public Set<Request> getRequestSet() {
        return Collections.unmodifiableSet(requestSet);
    }


    @Override
    public int hashCode() {
        return amka + 53;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Patient patient = (Patient) obj;
        if(this.amka != patient.amka){
            return false;
        }
        return true;
    }
}
