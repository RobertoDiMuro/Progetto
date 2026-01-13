package it.uniroma2.dicii.ezgym.domain.model;

public enum WorkoutDay {
    DUE_VOLTE("Due volte"),
    TRE_VOLTE("Tre volte"),
    QUATTRO_VOLTE("Quattro volte"),
    CINQUE_VOLTE("Cinque volte"),
    SEI_VOLTE("Sei volte");

    private final String label;

    WorkoutDay(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static WorkoutDay fromString(String raw){
        if (raw == null) {
            throw new IllegalArgumentException("Tipo esercizio nullo");
        }

        String s = raw.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Tipo esercizio vuoto");
        }

        for (WorkoutDay w : values()) {
            if (w.name().equalsIgnoreCase(s) || w.label.equalsIgnoreCase(s)) {
                return w;
            }
        }

        throw new IllegalArgumentException("Giorni di allenamento non validi: " + raw);
    }

    @Override
    public String toString() {
        return label;
    }
}
