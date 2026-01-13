package it.uniroma2.dicii.ezgym.domain.model;

public enum Target {
    PERDERE_PESO("Perdere peso"),
    MANTENERE("Mantenere"),
    MASSA_MUSCOLARE("Massa muscolare"),
    TONIFICARE("Tonificare");

     private final String label;

    Target(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Target fromString(String raw){
        if (raw == null) {
            throw new IllegalArgumentException("Tipo esercizio nullo");
        }

        String s = raw.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Tipo esercizio vuoto");
        }

        for (Target t : values()) {
            if (t.name().equalsIgnoreCase(s) || t.label.equalsIgnoreCase(s)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Livello di attività non valido: " + raw);
    }
   
}
