package it.uniroma2.dicii.EzGym.domain.model;

public class Athlete {
    private String name;
    private String surname;
    private String gender;
    private int age;
    private double weight;
    private double height;
    private Target target;
    private ActivityLevel activityLevel;
    private WorkoutDay workoutDay;

    public Athlete(String name, String surname, String gender, int age, double weight, double height,
                   Target target, ActivityLevel activityLevel, WorkoutDay workoutDay) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.activityLevel = activityLevel;
        this.workoutDay = workoutDay;
    }

     // Getters
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
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

    // Setters
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome non deve essere nullo");
        }
        this.name = name;
    }

    public void setSurname(String surname){
        if(surname == null || surname.trim().isEmpty()){
            throw new IllegalArgumentException("Il cognome non deve essere nullo");
        }
        this.surname = surname;
    }

    public void setAge(int age){
        if(age <= 0 || age > 120){
            throw new IllegalArgumentException("Età non valida");
        }
        this.age = age;
    }

    public void setWeight(double weight){
        if(weight <= 0 || weight > 500){
            throw new IllegalArgumentException("Peso non valido");
        }
        this.weight = weight;
    }

    public void setHeight(double height){
        if(height <= 0 || height > 300){
            throw new IllegalArgumentException("Altezza non valida");
        }
        this.height = height;
    }

    public void setTarget(Target target){
        if(target == null){
            throw new IllegalArgumentException("L'obiettivo non deve essere nullo");
        }
        this.target = target;
    }

    public void setActivityLevel(ActivityLevel activityLevel){
        if(activityLevel == null){
            throw new IllegalArgumentException("Il livello di attività non deve essere nullo");
        }
        this.activityLevel = activityLevel;
    }

    public void setWorkoutDay(WorkoutDay workoutDay){
        if(workoutDay == null){
            throw new IllegalArgumentException("I giorni di allenamento non devono essere nulli");
        }
        this.workoutDay = workoutDay;
    }

    public double calculateBMI(){
        if(height <= 0 || weight <= 0){
            throw new IllegalStateException("Altezza e peso devono essere maggiori di zero per calcolare il BMI");
        }
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }
}
