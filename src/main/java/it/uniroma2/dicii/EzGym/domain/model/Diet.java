package it.uniroma2.dicii.EzGym.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Diet {
    private final String requestByAthleteName;
    private final String requestByAthleteSurname;
    private final String createdByTrainerName;
    private final String createdByTrainerSurname;

    private Target target;
    private double targetCalories;
    private Macronutrients targetMacros;
    private List<DailyMealPlan> dailyPlans;
    private String notes;
    private DietStatus status;

    public Diet(String athleteName, String athleteSurname,
                String trainerNmame, String trainerSurname,
                Target target, double targetCalories, Macronutrients targetMacros,
                List<DailyMealPlan> dailyPlans, String notes, DietStatus status) {
        this.requestByAthleteName = athleteName;
        this.requestByAthleteSurname = athleteSurname;
        this.createdByTrainerName = trainerNmame;
        this.createdByTrainerSurname = trainerSurname;
        this.target = target;
        this.targetCalories = targetCalories;
        this.targetMacros = targetMacros;
        this.dailyPlans = new ArrayList<>(dailyPlans);
        this.notes = "";
        this.status = DietStatus.ACTIVE;
    }

    public String getRequestByAthleteName(){
        return requestByAthleteName;
    }
    public String getRequestByAthleteSurname(){
        return requestByAthleteSurname;
    }
    public String getCreatedByTrainerName(){
        return createdByTrainerName;
    }
    public String getCreatedByTrainerSurname(){
        return createdByTrainerSurname;
    }
    public String getRequestByAthleteFullName(){
        return requestByAthleteName + " " + requestByAthleteSurname;
    }
    public String getCreatedByTrainerFullName(){
        return createdByTrainerName + " " + createdByTrainerSurname;
    }
    public Target getTarget() {
        return target;
    }
    public double getTargetCalories() {
        return targetCalories;
    }
    public Macronutrients getTargetMacros() {
        return targetMacros;
    }
    public DietStatus getStatus() {
        return status;
    }
    public List<DailyMealPlan> getDailyPlans() {
        return Collections.unmodifiableList(dailyPlans);
    }
    public String getNotes() {
        return notes;
    }

    public void setTarget(Target target) {
        if(target == null) {
            throw new IllegalArgumentException("l'obiettivo non può essere nullo");
        }
        this.target = target;
    }
    public void setTargetCalories(double targetCalories) {
        if(targetCalories <= 0) {
            throw new IllegalArgumentException("le calorie target devono essere maggiori di zero");
        }
        this.targetCalories = targetCalories;
    }
    public void setTargetMacros(Macronutrients targetMacros) {
        if(targetMacros == null) {
            throw new IllegalArgumentException("i macronutrienti target non possono essere nulli");
        }
        this.targetMacros = targetMacros;
    }
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }
    public void setStatus(DietStatus status) {
        if(status == null) {
            throw new IllegalArgumentException("lo stato della dieta non può essere nullo");
        }
        this.status = status;
    }

    public void addDailyPlan(DailyMealPlan plan){
        if (plan == null) {
            throw new IllegalArgumentException("Il piano giornaliero non deve essere nullo");
        }
        if (this.status != DietStatus.ACTIVE) {
            throw new IllegalStateException("Non è possibile aggiungere piani ad una dieta non attiva");
        }
        this.dailyPlans.add(plan);
    }
    
    public void removeDailyPlan(DailyMealPlan plan) {
        if (this.status != DietStatus.ACTIVE) {
            throw new IllegalStateException("Non è possibile rimuovere piani da una dieta non attiva");
        }
        this.dailyPlans.remove(plan);
    }

    public void complete(){
        this.status = DietStatus.COPLETED;
    }
    public void suspend(){
        this.status = DietStatus.SUSPENDED;
    }
    public Macronutrients calculateAverageDailyMacronutrients() {
    if (dailyPlans.isEmpty()) {
        return new Macronutrients(0, 0, 0, 0);
    }
    
    double totalProteins = 0;
    double totalCarbs = 0;
    double totalFats = 0;
    double totalFibers = 0;
    
    for (DailyMealPlan plan : dailyPlans) {
        Macronutrients macro = plan.calculateTotalMacronutrients();
        totalProteins += macro.getProteins();
        totalCarbs += macro.getCarbohydrates();
        totalFats += macro.getFats();
        totalFibers += macro.getFibers();
    }
    
    int numDays = dailyPlans.size();
    return new Macronutrients(
        totalProteins / numDays,
        totalCarbs / numDays,
        totalFats / numDays,
        totalFibers / numDays
    );
}

public double calculateAverageDailyCalories() {
    return calculateAverageDailyMacronutrients().getTotalCalories();
}

}
