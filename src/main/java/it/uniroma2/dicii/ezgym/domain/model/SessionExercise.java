package it.uniroma2.dicii.ezgym.domain.model;

public class SessionExercise {
    
    private int sessionId;
    private String exerciseName;
    private int sets;
    private int reps;
    private double restTime;
    private ExerciseType type;
    private String notes;

    public SessionExercise(){
        //
    }

    public SessionExercise(int sessionId, String exerciseName, int sets, int reps, double restTime, ExerciseType type, String notes) {
        this.sessionId = sessionId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
        this.type = type;
        this.notes = notes;
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
        this.sessionId = sessionId;
    }
    public void setExerciseName(String exerciseName){
        this.exerciseName = exerciseName;
    }
    public void setSets(int sets){
        this.sets = sets;
    }
    public void setReps(int reps){
        this.reps = reps;
    }
    public void setRestTime(double restTime){
        this.restTime = restTime;
    }
    public void setType(ExerciseType type){
        this.type = type;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes : " ";
    }

}
