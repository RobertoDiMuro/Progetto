package it.uniroma2.dicii.EzGym.domain.bean;

import java.util.ArrayList;
import java.util.List;

public class DailyMealPlanBean {
    
    private String day;
    private List<MealBean> meals;
    private String notes;

    public DailyMealPlanBean(){
        this.meals = new ArrayList<>();
    }

    public String getDay(){
        return day;
    }

    public void setDay(String day){
        if(day == null || day.trim().isEmpty()){
            throw new IllegalArgumentException("Il giorno non può essere vuoto o nullo");
        }
        this.day = day;
    }

    public List<MealBean> getMeals(){
        return meals;
    }

    public void setMeals(List<MealBean> meals) {
        this.meals = meals != null ? new ArrayList<>(meals) : new ArrayList<>();
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
}
