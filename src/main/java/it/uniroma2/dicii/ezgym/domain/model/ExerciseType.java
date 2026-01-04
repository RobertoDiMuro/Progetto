package it.uniroma2.dicii.ezgym.domain.model;

public enum ExerciseType {
    Forza("Sviluppo e aumento capacità di forza e potenza"),
    Cardio("Aumento capacità cardiovascolari e aerobiche"),
    Pump("Tonificazione muscolare"),
    Pliometria("Aumento capacità reattivo-elastiche ed esplosività"),
    Ipertrofia("Crescita muscolare"),
    Funzionale("Recupero infortuni");

    private final String description;
    ExerciseType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


}
