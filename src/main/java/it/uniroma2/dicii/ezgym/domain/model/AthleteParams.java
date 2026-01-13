package it.uniroma2.dicii.ezgym.domain.model;

public record AthleteParams(
    String gender,
    int age, 
    double weight, 
    double height, 
    Target target, 
    ActivityLevel activityLevel, 
    WorkoutDay workoutDay, 
    Boolean isWorkoutRequested) {
}
