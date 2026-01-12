package it.uniroma2.dicii.ezgym.domain.model;

public enum ActivityLevel {
    SEDENTARIO("Sedentario"),
    LEGGERMENTE_ATTIVO("Leggermente attivo"),
    MODERATAMENTE_ATTIVO("Moderatamente attivo"),
    INTENSO("Intenso");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
