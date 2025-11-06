package it.uniroma2.dicii.ezgym.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Meal {
    private MealType type;
    private List<Food> portions;
    private String notes;

    public Meal(MealType type, List<Food> portions, String notes) {
        this.type = type;
        this.portions = new ArrayList<>(portions);
        this.notes = "";
    }
    public MealType getType() {
        return type;
    }
    public List<Food> getPortions() {
        return Collections.unmodifiableList(portions);
    }
    public String getNotes() {
        return notes;
    }

    public void setType(MealType type) {
        this.type = type;
    }
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }
    public void addPortion(Food portions){
        this.portions.add(portions);
    }
    public void removePortion(Food portion) {
        this.portions.remove(portion);
    }
    
    public Macronutrients calculateTotalMacronutrients() {
    double totalProteins = 0;
    double totalCarbs = 0;
    double totalFats = 0;
    double totalFibers = 0;
    
    for (Food food : portions) {
        Macronutrients macro = food.calculateMacronutrients();
        totalProteins += macro.getProteins();
        totalCarbs += macro.getCarbohydrates();
        totalFats += macro.getFats();
        totalFibers += macro.getFibers();
    }
    
    return new Macronutrients(totalProteins, totalCarbs, totalFats, totalFibers);
}
    public double calculateTotalCalories() {
        return calculateTotalMacronutrients().getTotalCalories();
    }
}
