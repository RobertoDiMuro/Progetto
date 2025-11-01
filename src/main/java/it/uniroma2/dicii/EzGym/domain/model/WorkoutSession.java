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
        this.sessionName = sessionName;
    }
    public void setDayOfWeek(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }
    public void setStatus(WorkoutStatus status){
        this.status = status;
    }
    public void setFocus(String focus){
        this.focus = focus;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes: "Nessuna nota aggiuntiva";
    }

    public void addExercise(Exercise exercise){
        this.exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise){
        this.exercises.remove(exercise);
    }
    
}
