package it.uniroma2.dicii.ezgym.bean;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.WorkoutStatus;

public class WorkoutSessionBean {
    
    private String sessionName;
    private String dayOfWeek;
    private WorkoutStatus status;
    private String focus;
    private List<ExerciseBean> exercises;
    private String notes;

    public WorkoutSessionBean(){
        this.exercises = new ArrayList<>();
    }

    public String getSessionName(){
        return sessionName;
    }

    public void setSessionName(String sessionName){
        if(sessionName == null || sessionName.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome della sessione non può essere vuoto o nullo");
        }
        this.sessionName = sessionName;
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

    public WorkoutStatus getStatus(){
        return status;
    }

    public void setStatus(WorkoutStatus status){
        if(status == null){
            throw new IllegalArgumentException("Lo stato non può esssre nullo");
        }
        this.status = status;
    }

    public String getFocus(){
        return focus;
    }

    public void setFocus(String focus){
        if(focus == null || focus.trim().isEmpty()){
            throw new IllegalArgumentException("Il focus non può essere vuoto o nullo");
        }
        this.focus = focus;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }

    public List<ExerciseBean> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseBean> exercises) {
        this.exercises = exercises != null ? new ArrayList<>(exercises) : new ArrayList<>();
    }
}
