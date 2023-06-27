package com.example.domain;

import java.util.Date;

public class Request implements ContainsDate{
    private final Patient patient;
    private final Description description;
    private final Date date;
	private final TherapyService therapyService;

    public Request(Description description, Patient patient, TherapyService therapyService) {
        this.description = description;
        this.patient = patient;
        this.therapyService = therapyService;
        this.date = new Date();
    }


    public Patient getPatient() {
        return patient;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public String showDate() {
        return date.toString();
    }
}

enum Description{
    ADD_APPOINTMENT,
    NOT_A_REQUEST
}