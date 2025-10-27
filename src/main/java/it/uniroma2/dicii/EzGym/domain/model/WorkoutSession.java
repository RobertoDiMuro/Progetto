package it.uniroma2.dicii.EzGym.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutSession {
    private String sessionName;
    private String dayOfWeek;
    private WorkoutStatus status;
    private String focus;
    private List<Exercise> exercises;
    private String notes;

    public WorkoutSession(String sessionName, String dayOfWeek, WorkoutStatus status, String focus, List<Exercise> exercises, String notes) {
        this.sessionName = sessionName;
        this.dayOfWeek = dayOfWeek;
        this.status = status;
        this.focus = focus;
        this.exercises = new ArrayList<>(exercises);
        this.notes = "";
    }

    // Getters
    public String getSessionName() {
        return sessionName;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public WorkoutStatus getStatus() {
        return status;
    }
    public String getFocus() {
        return focus;
    }
    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }
    public String getNotes() {
        return notes;
    }

    // Setters
    public void setSessionName(String sessionName){
        if(sessionName == null || sessionName.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome della sessione non deve essere nullo");
        }
        this.sessionName = sessionName;
    }
    public void setDayOfWeek(String dayOfWeek){
        if(dayOfWeek == null || dayOfWeek.trim().isEmpty()){
            throw new IllegalArgumentException("Il giorno della settimana non deve essere nullo");
        }
        this.dayOfWeek = dayOfWeek;
    }
    public void setStatus(WorkoutStatus status){
        if(status == null){
            throw new IllegalArgumentException("Lo stato dell'allenamento non deve essere nullo");
        }
        this.status = status;
    }
    public void setFocus(String focus){
        if(focus == null || focus.trim().isEmpty()){
            throw new IllegalArgumentException("Il focus dell'allenamento non deve essere nullo");
        }
        this.focus = focus;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes: "Nessuna nota aggiuntiva";
    }

    public void addExercise(Exercise exercise){
        if(exercise == null){
            throw new IllegalArgumentException("L'esercizio non deve essere nullo");
        }
        this.exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise){
        if(exercise == null){
            throw new IllegalArgumentException("L'esercizio non deve essere nullo");
        }
        this.exercises.remove(exercise);
    }
    
}
