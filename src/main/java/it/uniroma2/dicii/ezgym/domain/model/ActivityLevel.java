package it.uniroma2.dicii.ezgym.domain.model;

public enum ActivityLevel {
    SEDENTARIO("Sedentario"),
    LEGGERMENTE_ATTIVO("Leggermente attivo"),
    MODERATAMENTE_ATTIVO("Moderatamente attivo"),
    INTENSO("Intenso");

    private final String label;

    ActivityLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ActivityLevel fromString(String raw){
        if (raw == null) {
            throw new IllegalArgumentException("Tipo esercizio nullo");
        }

        String s = raw.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Tipo esercizio vuoto");
        }

        for (ActivityLevel a : values()) {
            if (a.name().equalsIgnoreCase(s) || a.label.equalsIgnoreCase(s)) {
                return a;
            }
        }

        throw new IllegalArgumentException("Livello di attività non valido: " + raw);
    }

    @Override
    public String toString() {
        return label;
    }
}
