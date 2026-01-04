package it.uniroma2.dicii.ezgym.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutBean {
    
    private int workoutId;
    private UUID athleteId;
    private int repeteWeeks;
    private List<WorkoutSessionBean> sessions;

    public WorkoutBean(){
        this.sessions = new ArrayList<>();
    }

    public int getWorkoutId(){
        return workoutId;
    }
    public void setWorkoutId(int workoutId){
        if(workoutId < 0){
            throw new IllegalArgumentException("L'id della scheda non può essere negativo.");
        }
        this.workoutId = workoutId;
    }

    public UUID getAthleteId(){
        return athleteId;
    }
    public void setAthleteId(UUID athleteId){
        this.athleteId = athleteId;
    }

    public int getRepeteWeeks(){
        return repeteWeeks;
    }
    public void setRepeteWeeks(int repeteWeeks){
        if(repeteWeeks < 0){
            throw new IllegalArgumentException("Il numero delle settimane deve essre positivo");
        }
        this.repeteWeeks = repeteWeeks;
    }

    public List<WorkoutSessionBean> getsSessions(){
        return sessions;
    }

    public void setSessions(List<WorkoutSessionBean> sessions) {
        this.sessions = (sessions != null) ? new ArrayList<>(sessions) : new ArrayList<>();
    }

}
