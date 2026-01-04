package it.uniroma2.dicii.ezgym.domain.model;

public enum ActivityLevel {
    Sedentario("Sedentario - Nessuna attività fisica regolare"),
    Leggermente_attivo("Leggermente attivo - Attività fisica leggera 1-2 volte a settimana"),
    Moderatamente_attivo("Moderatamente attivo - Attività fisica 3-4 volte a settimana"),
    Intenso("Intenso - Attività fisica intensa 5-6 volte a settimana");

    private final String description;

    ActivityLevel(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
