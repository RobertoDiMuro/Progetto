package it.uniroma2.dicii.EzGym.domain.model;

public class Food {
    private String name;
    private String category;
    private Macronutrients macronutrientsPer100g;
    private double grams;
    private String notes;

    public Food(String name, String category, Macronutrients macronutrientsPer100g,double grams, String notes) {
        this.name = name;
        this.category = category;
        this.macronutrientsPer100g = macronutrientsPer100g;
        this.grams = grams;
        this.notes = "";
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public Macronutrients getMacronutrientsPer100g() {
        return macronutrientsPer100g;
    }
    public double getGrams() {
        return grams;
    }
    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setNotes(String notes) {
        this.notes = notes == null ? notes : "";
    }
    public void setMacronutrientsPer100g(Macronutrients macronutrientsPer100g) {
        this.macronutrientsPer100g = macronutrientsPer100g;
    }
    public void setGrams(double grams) {
        this.grams = grams;
    }

    public Macronutrients calculateMacronutrients(double grams) {
        return macronutrientsPer100g.multiply(grams / 100.0);
    }

    public Macronutrients calculateMacronutrients() {
        return calculateMacronutrients(grams);
    }
    public double calculateCalories() {
        return calculateMacronutrients().getTotalCalories();
    }
}
