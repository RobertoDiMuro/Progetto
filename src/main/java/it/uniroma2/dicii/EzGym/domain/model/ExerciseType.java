package it.uniroma2.dicii.EzGym.domain.model;

public enum ExerciseType {
    FORZA("Sviluppo e aumento capacità di forza e potenza"),
    CARDIO("Aumento capacità cardiovascolari e aerobiche"),
    PUMP("Tonificazione muscolare"),
    PLIOMETRIA("Aumento capacità reattivo-elastiche ed esplosività"),
    IPERTROFIA("Crescita muscolare"),
    FUNZIONALE("Recupero infortuni");

    private final String description;
    ExerciseType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


}
