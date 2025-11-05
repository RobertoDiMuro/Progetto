package it.uniroma2.dicii.ezgym.domain.model;

public enum MacronutrientType {
    PROTEINS("proteine", 4.0),
    CARBOHYDRATES("carboidrati", 4.0),
    FATS("grassi", 9.0),
    FIBERS("fibre", 0.0);

    private final String description;
    private final double caloriesPerGram;

    MacronutrientType(String description, double caloriesPerGram) {
        this.description = description;
        this.caloriesPerGram = caloriesPerGram;
    }

    public String getDescription() {
        return description;
    }
    public double getCaloriesPerGram() {
        return caloriesPerGram;
    }
    
    
}
