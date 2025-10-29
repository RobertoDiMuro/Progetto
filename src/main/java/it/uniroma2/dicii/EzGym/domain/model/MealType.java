package it.uniroma2.dicii.EzGym.domain.model;

public enum MealType {
    BREAKFAST("Colazione"),
    LUNCH("Pranzo"),
    DINNER("Cena"),
    SNACK("Spuntino");

    private final String description;
    MealType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
