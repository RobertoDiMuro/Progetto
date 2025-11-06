package it.uniroma2.dicii.ezgym.domain.model;

public enum ActivityLevel {
    SEDENTARIO("Sedentario - Nessuna attività fisica regolare"),
    LEGGERMENTE_ATTIVO("Leggermente attivo - Attività fisica leggera 1-2 volte a settimana"),
    MODERATAMENTE_ATTIVO("Moderatamente attivo - Attività fisica 3-4 volte a settimana"),
    INTENSO("Intenso - Attività fisica intensa 5-6 volte a settimana");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
