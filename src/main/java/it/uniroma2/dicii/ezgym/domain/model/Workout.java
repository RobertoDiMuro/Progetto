package it.uniroma2.dicii.ezgym.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workout {

    private final String requestByAthleteName;
    private final String requestByAthleteSurname;
    private final String createdByTrainerName;
    private final String createdByTrainerSurname;

    private Target target;
    private String notes;
    private List<WorkoutSession> sessions;
    private WorkoutStatus status;

    public Workout(String athleteName, String athleteSurname, String trainerName, String trainerSurname,
                   Target target, String notes, List<WorkoutSession> sessions, WorkoutStatus status) {
        this.requestByAthleteName = athleteName;
        this.requestByAthleteSurname = athleteSurname;
        this.createdByTrainerName = trainerName;
        this.createdByTrainerSurname = trainerSurname;
        this.target = target;
        this.notes = "";
        this.sessions = new ArrayList<>();
        this.status = WorkoutStatus.ACTIVE;
    }

    public String getRequestByAthleteName(){
        return requestByAthleteName;
    }
    public String getRequestByAthleteSurname(){
        return requestByAthleteSurname;
    }
    public String getCreatedByTrainerName(){
        return createdByTrainerName;
    }
    public String getCreatedByTrainerSurname(){
        return createdByTrainerSurname;
    }
    public String getRequestByAthleteFullName(){
        return requestByAthleteName + " " + requestByAthleteSurname;
    }
    public String getCreatedByTrainerFullName(){
        return createdByTrainerName + " " + createdByTrainerSurname;
    }
    public Target getTarget() {
        return target;
    }
    public WorkoutStatus getStatus() {
        return status;
    }
    public String getNotes(){
        return notes;
    }
    public List<WorkoutSession> getSessions(){
        return Collections.unmodifiableList(sessions);
    }

    // Setters
    public void setTarget(Target target){
        this.target = target;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }

    public void setStatus(WorkoutStatus status){
        this.status = status;
    }

    public void addSession(WorkoutSession session){
        this.sessions.add(session);
    }

    public void removeSession(WorkoutSession session){
        this.sessions.remove(session);
    }
     public void skip() {
        this.status = WorkoutStatus.SKIPPED;
    }

    public void complete() {
        this.status = WorkoutStatus.COMPLETED;
    }

    public void suspend() {
        this.status = WorkoutStatus.SUSPENDED;
    }


}
