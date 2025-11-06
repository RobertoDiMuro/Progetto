package it.uniroma2.dicii.ezgym.bean;

import it.uniroma2.dicii.ezgym.domain.model.Macronutrients;

public class FoodBean {
    
    private String name;
    private String category;
    private Macronutrients macronutrientsPer100g;
    private double grams;
    private String notes;

    public FoodBean(){}

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome non può essere vuoto o nullo");
        }
        this.name  = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        if(category == null || category.trim().isEmpty()){
            throw new IllegalArgumentException("La categoria non può essere vuota o nulla");
        }
        this.category  = category;
    }

    public Macronutrients getMacronutrientsPer100g(){
        return macronutrientsPer100g;
    }

    public void setMacronutrientsPer100g(Macronutrients macronutrientsPer100g){
        if(macronutrientsPer100g == null){
            throw new IllegalArgumentException("I macronutrienti per 100g non possono essere nulli");
        }
        this.macronutrientsPer100g = macronutrientsPer100g;
    }

    public double getGrams(){
        return grams;
    }

    public void setGrams(double grams){
        if(grams < 0){
            throw new IllegalArgumentException("I grammi devono essere positivi");
        }
        this.grams = grams;
    }

    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "Nessuna nota aggiuntiva";
    }
}
