package it.uniroma2.dicii.ezgym.domain.model;

public enum ExerciseType {
    FORZA("Forza"),
    CARDIO("Cardio"),
    PUMP("Pump"),
    PLIOMETRIA("Pliometria"),
    IPERTROFIA("Ipertrofia"),
    FUNZIONALE("Funzionale");

    private final String label;

    ExerciseType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ExerciseType fromString(String raw){
        if (raw == null) {
            throw new IllegalArgumentException("Tipo esercizio nullo");
        }

        String s = raw.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Tipo esercizio vuoto");
        }

        for (ExerciseType t : values()) {
            if (t.name().equalsIgnoreCase(s) || t.label.equalsIgnoreCase(s)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Tipo esercizio non valido: " + raw);
    }

}
