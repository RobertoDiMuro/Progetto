package it.uniroma2.dicii.EzGym.domain.model;

public class Exercise {

    private String name;
    private int sets;
    private int reps;
    private int restTime;
    private String notes;
    private ExerciseType type;

    public Exercise(String name, int sets, int reps, int restTime, String notes, ExerciseType type) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
        this.notes = "";
        this.type = type;
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getSets() {
        return sets;
    }
    public int getReps() {
        return reps;
    }
    public int getRestTime() {
        return restTime;
    }
    public String getNotes() {
        return notes;
    }
    public ExerciseType getType() {
        return type;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }

    public void setSets(int sets){
        this.sets = sets;
    }

    public void setReps(int reps){
        this.reps = reps;
    }
    public void setRestTime(int restTime){
        this.restTime = restTime;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }

    public void setType(ExerciseType type){
        this.type = type;
    }

}
