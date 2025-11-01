package it.uniroma2.dicii.EzGym.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyMealPlan {
    private String day;
    private List<Meal> meals;
    private String notes;

    public DailyMealPlan(String day, List<Meal> meals, String notes) {
        this.day = day;
        this.meals = new ArrayList<>(meals);
        this.notes = "";
    }
    public String getDay() {
        return day;
    }
    public String getNotes() {
        return notes;
    }
    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public void setDay(String day) {
        this.day = day;
    }
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    public void addMeal(Meal meal) {
        this.meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        this.meals.remove(meal);
    }
    
    public Macronutrients calculateTotalMacronutrients() {
        double totalProteins = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        double totalFibers = 0;
        
        for (Meal meal : meals) {
            Macronutrients macro = meal.calculateTotalMacronutrients();
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

