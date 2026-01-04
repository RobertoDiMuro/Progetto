package it.uniroma2.dicii.ezgym.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Workout {

    private int workoutId;
    private UUID athleteId;
    private int repeteWeeks;
    private List<WorkoutSession> sessions;
    

    public Workout( int workoutId, UUID athleteId, int repeteWeeks ,List<WorkoutSession> sessions) {
        
        this.workoutId = workoutId;
        this.athleteId = athleteId;
        this.repeteWeeks = repeteWeeks;
        this.sessions = sessions;
    }

    
    public int getWorkoutId(){
        return workoutId;
    }
    public UUID getAthleteId(){
        return athleteId;
    }

    public int getRepeteWeeks(){
        return repeteWeeks;
    }
    
    public List<WorkoutSession> getSessions(){
        return Collections.unmodifiableList(sessions);
    }

    // Setters
    public void setWorkoutId(int workoutId){
        this.workoutId = workoutId;
    }
    public void setAthleteId(UUID athleteId){
        this.athleteId = athleteId;
    }
    public void setRepeteWeeks(int repeteWeeks){
        this.repeteWeeks = repeteWeeks;
    }
    public void setSessions(List<WorkoutSession> sessions){
        this.sessions = sessions;
    }

}
