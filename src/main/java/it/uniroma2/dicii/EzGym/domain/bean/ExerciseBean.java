package it.uniroma2.dicii.EzGym.domain.bean;

import it.uniroma2.dicii.EzGym.domain.model.ExerciseType;

public class ExerciseBean {

    private String name;
    private int sets;
    private int reps;
    private int restTime;
    private String notes;
    private ExerciseType type;

    public ExerciseBean() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome dell'esercizio non può essere vuoto o nullo.");
        }
        this.name = name;
    }
    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        if (sets <= 0){
            throw new IllegalArgumentException("Il numero di serie deve essere positivo.");
        }
        this.sets = sets;
    }
    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        if (reps <= 0){
            throw new IllegalArgumentException("Il numero di ripetizioni deve essere positivo.");
        }
        this.reps = reps;
    }
    public int getRestTime() {
        return restTime;
    }
    public void setRestTime(int restTime) {
        if (restTime < 0){
            throw new IllegalArgumentException("Il tempo di riposo non può essere negativo.");
        }
        this.restTime = restTime;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
    public ExerciseType getType() {
        return type;
    }
    public void setType(ExerciseType type) {
        if( type == null){
            throw new IllegalArgumentException("Il tipo di esercizio non può essere nullo.");
        }
        this.type = type;
    }
}
