package it.uniroma2.dicii.ezgym.bean;

import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
 
public class AthleteBean extends UserBean {
    
    private String gender;
    private int age;
    private double weight;
    private double height;
    private Target target;
    private ActivityLevel activityLevel;
    private WorkoutDay workoutDay;
    private boolean isWorkoutRequested;

    public AthleteBean(){
        // Costruttore vuoto
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        if(gender == null || gender.trim().isEmpty()){
            throw new IllegalArgumentException("Il genere non può essere vuoto o nullo");
        }
        this.gender = gender;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("L'età non è valida, inserire un numero compreso tra 1 e 120.");
        }
        this.age = age;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0 || weight > 500) {
            throw new IllegalArgumentException("Peso non valido, inserire un numero compreso tra 1 e 500.");
        }
        this.weight = weight;
    }

    public double getHeight(){
        return height;
    }

     public void setHeight(double height) {
        if (height <= 0 || height > 300) {
            throw new IllegalArgumentException("L'altzza non è valida, insrire un numero tra 1 e 300.");
        }
        this.height = height;
    }

    public Target getTarget(){
        return target;
    }

    public void setTarget(Target target) {
        if (target == null) {
            throw new IllegalArgumentException("Il target non può essere nullo.");
        }
        this.target = target;
    }

    public ActivityLevel getActivityLevel(){
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        if (activityLevel == null) {
            throw new IllegalArgumentException("Il livello di attività non può essere nullo.");
        }
        this.activityLevel = activityLevel;
    }

    public WorkoutDay getWorkoutDay(){
        return workoutDay;
    }

    public void setWorkoutDay(WorkoutDay workoutDay) {
        if (workoutDay == null) {
            throw new IllegalArgumentException("Il giorno di allenamento non può essere nullo.");
        }
        this.workoutDay = workoutDay;
    }

    public boolean getIsWorkoutRequested() {
        return isWorkoutRequested;
    }

    public void setIsWorkoutRequested(boolean isWorkoutRequested){
        this.isWorkoutRequested = isWorkoutRequested;
    }
}
