package it.uniroma2.dicii.ezgym.domain.model;

import java.util.UUID;

public class Athlete extends User {
    private String gender;
    private int age;
    private double weight;
    private double height;
    private Target target;
    private ActivityLevel activityLevel;
    private WorkoutDay workoutDay;
    private boolean isWorkoutRequested;

    
    public Athlete() {
        // costruttore vuoto 
    }

    public Athlete( String gender, int age, UUID id, String name, String surname, String email, String password, Role role, double weight, double height, Target target, ActivityLevel activityLevel, WorkoutDay workoutDay, Boolean isWorkoutRequested) {
        super(id, name, surname, email, password, role);
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.activityLevel = activityLevel;
        this.workoutDay = workoutDay;
        this.isWorkoutRequested = isWorkoutRequested;
    }

    public String getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public double getWeight() {
        return weight;
    }
    public double getHeight() {
        return height;
    }
    public Target getTarget() {
        return target;
    }
    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }
    public WorkoutDay getWorkoutDay() {
        return workoutDay;
    }
    public boolean getIsWorkoutRequested() {
        return isWorkoutRequested;
    }

    // Setters


    public void setAge(int age){
        this.age = age;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setTarget(Target target){
        this.target = target;
    }

    public void setActivityLevel(ActivityLevel activityLevel){
        this.activityLevel = activityLevel;
    }

    public void setWorkoutDay(WorkoutDay workoutDay){
        this.workoutDay = workoutDay;
    }

    public void setIsWorkoutRequested(boolean isWorkoutRequested){
        this.isWorkoutRequested = isWorkoutRequested;
    }

    public double calculateBMI() {
        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);
        return Math.round(bmi * 100.0) / 100.0;
    }
}
