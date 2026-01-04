package it.uniroma2.dicii.ezgym.domain.model;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSession {
    
    private int sessionId;
    private String dayOfWeek;
    private List<SessionExercise> exercises;

    public WorkoutSession(int sessionId, String dayOfWeek, List<SessionExercise> exercises) {
        this.sessionId = sessionId;
        this.dayOfWeek = dayOfWeek;
        this.exercises = new ArrayList<>(exercises);
    }

    // Getters
    public int getSessionId() {
        return sessionId;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
  
    public List<SessionExercise> getExercises() {
        return exercises;
    }

    // Setters
    public void setSessionId(int sessionId){
        this.sessionId = sessionId;
    }
    public void setDayOfWeek(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }
    public void setExercises(List<SessionExercise> exercises){
        this.exercises = exercises;
    }
    
}
