package it.uniroma2.dicii.EzGym.domain.bean;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.EzGym.domain.model.DietStatus;
import it.uniroma2.dicii.EzGym.domain.model.Macronutrients;
import it.uniroma2.dicii.EzGym.domain.model.Target;

public class DietBean {
    
    private String requestByAthleteName;
    private String requestByAthleteSurname;
    private String createdByTrainerName;
    private String createdByTrainerSurname;

    private Target target;
    private double targetCalories;
    private Macronutrients targetMacros;
    private List<DailyMealPlanBean> dailyPlans;
    private String notes;
    private DietStatus status;

    public DietBean(){
        this.dailyPlans = new ArrayList<>();
    }

     public String getRequestByAthleteName(){
        return requestByAthleteName;
    }

    public void setRequestByAthleteName(String requestByAthleteName) {
        if (requestByAthleteName == null || requestByAthleteName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome dell'atleta non può essere vuoto.");
        }
        this.requestByAthleteName = requestByAthleteName.trim();
    }

    public String getRequestByAthleteSurname(){
        return requestByAthleteSurname;
    }

    public void setRequestByAthleteSurname(String requestByAthleteSurname) {
        if (requestByAthleteSurname == null || requestByAthleteSurname.trim().isEmpty()) {
            throw new IllegalArgumentException("Il cognome dell'atleta non può essere vuoto.");
        }
        this.requestByAthleteSurname = requestByAthleteSurname.trim();
    }

    public String getCreatedByTrainerName(){
        return createdByTrainerName;
    }

     public void setCreatedByTrainerName(String createdByTrainerName) {
        if (createdByTrainerName == null || createdByTrainerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del trainer non può essere vuoto.");
        }
        this.createdByTrainerName = createdByTrainerName.trim();
    }

    public String getCreatedByTrainerSurname(){
        return createdByTrainerSurname;
    }

    public void setCreatedByTrainerSurname(String createdByTrainerSurname) {
        if (createdByTrainerSurname == null || createdByTrainerSurname.trim().isEmpty()) {
            throw new IllegalArgumentException("Il cognome del trainer non può essere vuoto.");
        }
        this.createdByTrainerSurname = createdByTrainerSurname.trim();
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        if (target == null) {
            throw new IllegalArgumentException("Il target non può essere nullo.");
        }
        this.target = target;
    }

    public double getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(double targetCalories){
        if(targetCalories < 0){
            throw new IllegalArgumentException("Il numero di calorie deve essere positivo");
        }
        this.targetCalories = targetCalories;
    }

    public Macronutrients getTargetMacros() {
        return targetMacros;
    }

    public void setTargetMacros(Macronutrients targetMacros){
        if(targetMacros == null){
            throw new IllegalArgumentException("I macronutrienti non possono essere nulli");
        }
        this.targetMacros = targetMacros;
    }
    public DietStatus getStatus() {
        return status;
    }

    public void setStatus(DietStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Lo stato non può essere nullo.");
        }
        this.status = status;
    }

    public List<DailyMealPlanBean> getDailyPlans() {
        return dailyPlans;
    }

    public void setMeals(List<DailyMealPlanBean> dailyPlans) {
        this.dailyPlans = dailyPlans != null ? new ArrayList<>(dailyPlans) : new ArrayList<>();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
}
