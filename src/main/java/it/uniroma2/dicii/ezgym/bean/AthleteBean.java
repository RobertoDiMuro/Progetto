package it.uniroma2.dicii.ezgym.bean;

import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
 //gsdvhs
public class AthleteBean extends UserBean {
    
    private String gender;
    private int age;
    private double weight;
    private double height;
    private Target target;
    private ActivityLevel activityLevel;
    private WorkoutDay workoutDay;

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
        if (age <= 0) {
            throw new IllegalArgumentException("L'età deve essere positiva.");
        }
        this.age = age;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Il peso deve essere positivo.");
        }
        this.weight = weight;
    }

    public double getHeight(){
        return height;
    }

     public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("L'altezza deve essere positiva.");
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

    public WorkoutDay geWorkoutDay(){
        return workoutDay;
    }

    public void setWorkoutDay(WorkoutDay workoutDay) {
        if (workoutDay == null) {
            throw new IllegalArgumentException("Il giorno di allenamento non può essere nullo.");
        }
        this.workoutDay = workoutDay;
    }

}
