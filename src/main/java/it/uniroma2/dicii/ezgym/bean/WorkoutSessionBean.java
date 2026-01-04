package it.uniroma2.dicii.ezgym.bean;

import java.util.ArrayList;
import java.util.List;


public class WorkoutSessionBean {
    
    private int sessionId;
    private String dayOfWeek;
    private List<SessionExerciseBean> exercises;

    public WorkoutSessionBean(){
        this.exercises = new ArrayList<>();
    }

    public int getSessionId(){
        return sessionId;
    }

    public void setSessionId(int sessionId){
        if(sessionId < 0){
            throw new IllegalArgumentException("L'id della sessione deve essere positivo");
        }
        this.sessionId = sessionId;
    }

    public String getDayOfWeek(){
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek){
        if(dayOfWeek == null || dayOfWeek.trim().isEmpty()){
            throw new IllegalArgumentException("Il giorno non può essere vuoto o nullo");
        }
        this.dayOfWeek = dayOfWeek;
    }

    public List<SessionExerciseBean> getExercises() {
        return exercises;
    }

    public void setExercises(List<SessionExerciseBean> exercises) {
        this.exercises = exercises != null ? new ArrayList<>(exercises) : new ArrayList<>();
    }
}
