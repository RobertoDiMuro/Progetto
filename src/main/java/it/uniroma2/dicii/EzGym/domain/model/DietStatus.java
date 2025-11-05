package it.uniroma2.dicii.ezgym.domain.model;

public enum DietStatus {
    ACTIVE("Attiva"),
    COPLETED("Completata"),
    SUSPENDED("Sospesa");

    private final String description;
    DietStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
