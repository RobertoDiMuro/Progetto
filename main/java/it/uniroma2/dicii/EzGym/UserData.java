package it.uniroma2.dicii.EzGym;

public class UserData {
    private static UserData instance;

    private String username;
    private String gender;
    private int age;
    private Double weight;
    private Double height;
    private String goal;

    private boolean hasWorkoutPlan;
    private int activeWeeks;
    private int completedWorkouts;
    private int weeklyWorkouts;
    private String activityLevel;

    private int dailyCalories;

    private UserData(){
        this.username = Main.USERNAME;
        this.hasWorkoutPlan = false;
        this.activeWeeks = 0;
        this.completedWorkouts = 0;
        this.dailyCalories = 0;
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public void setWorkoutPlanData(String gender, int age, double weight, double height, 
                                   String goal, String activityLevel, int weeklyWorkouts) {
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.weeklyWorkouts = weeklyWorkouts;
        this.hasWorkoutPlan = true;
        this.activeWeeks = 1;
        
        calculateDailyCalories();
    }

    public double getBmi() {
        if (height > 0) {
            double heightInMeters = height / 100.0;
            return weight / (heightInMeters * heightInMeters);
        }
        return 0.0;
    }

     private void calculateDailyCalories() {
        double bmr;

        if ("Maschio".equalsIgnoreCase(gender)) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }

        double activityMultiplier = 1.2; 
        if (activityLevel != null) {
            switch (activityLevel.toLowerCase()) {
                case "sedentario":
                    activityMultiplier = 1.2;
                    break;
                case "leggermente attivo":
                    activityMultiplier = 1.375;
                    break;
                case "moderatamente attivo":
                    activityMultiplier = 1.55;
                    break;
                case "molto attivo":
                    activityMultiplier = 1.725;
                    break;
                case "estremamente attivo":
                    activityMultiplier = 1.9;
                    break;
            }
        }

        double tdee = bmr * activityMultiplier;

        if("Perdere peso".equalsIgnoreCase(goal)) {
            tdee -= 500;
        } else if ("Massa muscolare".equalsIgnoreCase(goal)) {
            tdee += 500;
        }

        this.dailyCalories = (int) Math.round(tdee);
    }

    public void incrementCompletedWorkouts() {
        this.completedWorkouts++;
    }

    public void incrementActiveWeeks() {
        this.activeWeeks++;
    }

    public String getUsername() {
        return username;
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

    public String getGoal() {
        return goal != null ? goal : "Nessun obiettivo";
    }

    public boolean hasWorkoutPlan() {
        return hasWorkoutPlan;
    }

    public int getActiveWeeks() {
        return activeWeeks;
    }

    public int getCompletedWorkouts() {
        return completedWorkouts;
    }

    public int getWeeklyWorkouts() {
        return weeklyWorkouts;
    }

    public int getDailyCalories() {
        return dailyCalories;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setActiveWeeks(int activeWeeks) {
        this.activeWeeks = activeWeeks;
    }

    public void setCompletedWorkouts(int completedWorkouts) {
        this.completedWorkouts = completedWorkouts;
    }

    public void resetWorkoutPlan() {
        this.hasWorkoutPlan = false;
        this.activeWeeks = 0;
        this.completedWorkouts = 0;
        this.weeklyWorkouts = 0;
        this.dailyCalories = 0;
        this.goal = null;
    }
}
