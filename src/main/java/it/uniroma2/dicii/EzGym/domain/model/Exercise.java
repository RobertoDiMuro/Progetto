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
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome dell'esercizio non deve essere nullo");
        }
        this.name = name;
    }

    public void setSets(int sets){
        if(sets <= 0){
            throw new IllegalArgumentException("Il numero di serie deve essere positivo");
        }
        this.sets = sets;
    }

    public void setReps(int reps){
        if(reps <= 0){
            throw new IllegalArgumentException("Il numero di ripetizioni deve essere positivo");
        }
        this.reps = reps;
    }
    public void setRestTime(int restTime){
        if(restTime < 0){
            throw new IllegalArgumentException("Il tempo di riposo non puo' essere negativo");
        }
        this.restTime = restTime;
    }
    public void setNotes(String notes){
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }

    public void setType(ExerciseType type){
        if(type == null){
            throw new IllegalArgumentException("Il tipo di esercizio non deve essere nullo");
        }
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s: %dx%d (recupero: %ds)", 
                    name, sets, reps, restTime);
    }

}
