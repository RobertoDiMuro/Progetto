package it.uniroma2.dicii.EzGym.domain.model;

public class PersonalTrainer {
    private String name;
    private String surname;
    private double activeUsers;

    public PersonalTrainer(String name, String surname, double activeUsers, UserRole role) {
        this.name = name;
        this.surname = surname;
        this.activeUsers = activeUsers;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public double getActiveUsers() {
        return activeUsers;
    }

    // Setters
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome non deve essere nullo");
        }
        this.name = name;
    }
    public void setSurname(String surname){
        if(surname == null || surname.trim().isEmpty()){
            throw new IllegalArgumentException("Il cognome non deve essere nullo");
        }
        this.surname = surname;
    }
    public void setActiveUsers(double activeUsers){
        if(activeUsers < 0){
            throw new IllegalArgumentException("Il numero di utenti attivi non puo' essere negativo");
        }
        this.activeUsers = activeUsers;
    }
}
