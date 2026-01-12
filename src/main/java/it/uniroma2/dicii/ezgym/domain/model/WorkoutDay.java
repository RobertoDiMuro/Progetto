package it.uniroma2.dicii.ezgym.domain.model;

public enum WorkoutDay {
    DUEVOLTE("Due volte"),
    TREVOLTE("Tre volte"),
    QUATTROVOLTE("Quattro volte"),
    CINQUEVOLTE("Cinque volte"),
    SEIVOLTE("Sei volte");

     private final String description;

    WorkoutDay(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
