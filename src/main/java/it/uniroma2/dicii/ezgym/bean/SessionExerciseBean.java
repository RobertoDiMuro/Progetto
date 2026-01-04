package it.uniroma2.dicii.ezgym.bean;

import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;

public class SessionExerciseBean {
    
    private int sessionId;
    private String exerciseName;
    private int sets;
    private int reps;
    private double restTime;
    private ExerciseType type;
    private String notes;

    public SessionExerciseBean() {
        // Costruttore vuoto
    }

    public int getSessionId() {
        return sessionId;
    }
    public String getExerciseName() {
        return exerciseName;
    }
    public int getSets(){
        return sets;
    }
    public int getReps(){
        return reps;
    }
    public double getRestTime(){
        return restTime;
    }
    public ExerciseType getType(){
        return type;
    }
    public String getNotes(){
        return notes;
    }

    public void setSessionId(int sessionId){
        if(sessionId < 0){
            throw new IllegalArgumentException("L'ID della sessione non può essere negativo.");
        }
        this.sessionId = sessionId;
    }
    public void setExerciseName(String exerciseName){
        if (exerciseName == null || exerciseName.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto o nullo.");
        }
        this.exerciseName = exerciseName;
    }
    public void setSets(int sets){
        if(sets <= 0){
            throw new IllegalArgumentException("Il numero di serie deve essere positivo.");
        }
        this.sets = sets;
    }
    public void setReps(int reps){
        if(reps <= 0){
            throw new IllegalArgumentException("Il numero di ripetizioni deve essere positivo.");
        }
        this.reps = reps;
    }
    public void setRestTime(double restTime){
        if(restTime < 0){
            throw new IllegalArgumentException("Il tempo di recupero non può essere negativo.");
        }
        this.restTime = restTime;
    }
    public void setType(ExerciseType type){
        if(type == null){
            throw new IllegalArgumentException("Il tipo di esercizio non può essere nullo.");
        }
        this.type = type;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes : "";
    }
}
