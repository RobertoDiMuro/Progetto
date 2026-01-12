package it.uniroma2.dicii.ezgym.domain.model;

public enum ActivityLevel {
    SEDENTARIO("Sedentario"),
    LEGGERMENTEATTIVO("Leggermente attivo"),
    MODERATAMENTEATTIVO("Moderatamente attivo"),
    INTENSO("Intenso");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
