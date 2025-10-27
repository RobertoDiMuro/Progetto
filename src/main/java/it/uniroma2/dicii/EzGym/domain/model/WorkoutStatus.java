package it.uniroma2.dicii.EzGym.domain.model;

public enum WorkoutStatus {
    ACTIVE("Attivo"),
    COMPLETED("Completato"),
    SKIPPED("Saltato"),
    SUSPENDED("Sospeso");

    private final String description;
    WorkoutStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
