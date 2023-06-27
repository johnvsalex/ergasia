package com.example.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Example of usage based on Requirements(?).
 *
 *         QueryCustom<Patient> queryCustom = patient -> patient.getAmka() == 50;
 *         Doctor d = new Doctor();
 *         d.manageRequests(new Request(Description.ADD_APPOINTMENT, new Patient(00)));
 *         d.selectPatient(queryCustom); // null
 *
 *
 *         Many questions about how it should work.
 *
 *         Ex. what does addVisit() do? Does Visit contains a Patient? Is it subclass of Request?
 *         Shouldn't the Data Layer(Repository) care about CRUD operations - not Doctor?
 */

public class Doctor {
    private final List<Request> requests = new ArrayList<>();
    private final Set<Patient> patientSet = new HashSet<>();
    private final List<Visit> visits = new ArrayList<>();

    public void createPatient(String name, String address, int amka){
        patientSet.add(new Patient(name, address, amka));
    }

    public List<ContainsDate> showSchedule(){
        List<ContainsDate> schedule = new ArrayList<>(requests);
        schedule.addAll(visits);
        return schedule;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean searchPatient(QueryCustom<Patient> query){
        return QueryCustom.contains(patientSet, query);
    }

    public boolean searchPatient(int amka){
        return patientSet.contains(new Patient(amka));
    }
	
	public void acceptRequest(@NonNull Request request){
        Patient patient = request.getPatient();
        patient.withdraw(request.getTherapyService().getCost());
        patientSet.add(patient);
        requests.remove(request);
        patient.remove(request);
        addVisit(new Visit(patient));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Patient> selectPatient(QueryCustom<Patient> query){
        return QueryCustom.getFirstInstance(patientSet, query);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Patient> selectPatient(int amka){
        return patientSet.stream().filter(patient -> patient.getAmka() == amka).findFirst();
    }

    public List<Visit> showPatientHistory(){
        return Collections.unmodifiableList(visits);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Request> showAppointmentRequests(){
        return requests.stream().filter(request -> request.getDescription().equals(Description.ADD_APPOINTMENT)).collect(Collectors.toList());
    }

    public String manageRequests(Request request){
        switch (request.getDescription()){
            case ADD_APPOINTMENT:
                addAppointment(request);
                return Description.ADD_APPOINTMENT.name();
            default:
                return Description.NOT_A_REQUEST.name();
        }
    }

    private boolean addAppointment(Request request){
        if(requests.add(request) && patientSet.add(request.getPatient())){
            return true;
        }
        return false;
    }

    public void addVisit(Visit visit){
        visits.add(visit);
    }

    public void recordActions(Visit visit, String... actions){
        visits.get(visits.indexOf(visit)).addActions(actions);
    }
}

class Visit implements ContainsDate{
    private final List<String> actions = new ArrayList<>();
    private final Date date;
	private final Patient patient;

    public Visit(Patient patient) {
		this.patient = patient;
        this.date = new Date();
    }

    public Visit addActions(String... actions) {
        this.actions.addAll(Arrays.asList(actions));
        return this;
    }

    @Override
    public String showDate() {
        return date.toString();
    }
}

@FunctionalInterface
interface QueryCustom<T> {
    boolean apply(T t);

    @RequiresApi(api = Build.VERSION_CODES.N)
    static <T> Set<T> filterSet(Set<T> set, QueryCustom<T> query) {
        return set.stream().filter(query::apply).collect(Collectors.toSet());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static <T> Optional<T> getFirstInstance(Set<T> set, QueryCustom<T> query) {
        return set.stream().filter(query::apply).findFirst();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static <T> boolean contains(Set<T> set, QueryCustom<T> query) {
        return set.stream().anyMatch(query::apply);
    }
}

