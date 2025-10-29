package it.uniroma2.dicii.EzGym.domain.model;

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
        if(type == null) {
            throw new IllegalArgumentException("il tipo di pasto non può essere nullo");
        }
        this.type = type;
    }
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }
    public void addPortion(Food portions){
        if(portions == null){
            throw new IllegalArgumentException("La porzione non deve essere nulla");
        }
        this.portions.add(portions);
    }
    public void removePortion(Food portion) {
        if (portion == null) {
            throw new IllegalArgumentException("La porzione non deve essere nulla");
        }
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
