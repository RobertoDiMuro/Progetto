package it.uniroma2.dicii.ezgym.domain.model;

public enum Target {
    PERDEREPESO("Perdere peso"),
    MANTENERE("Mantenere"),
    MASSAMUSCOLARE("Massa muscolare"),
    TONIFICARE("Tonificare");

     private final String description;

    Target(String description) {
        this.description = description;
    }

    public String getDescrizione() {
        return description;
    }
}
