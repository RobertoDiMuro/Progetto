package it.uniroma2.dicii.EzGym.domain.model;

public class PersonalTrainer extends User {
    private double activeUsers;

    public PersonalTrainer(String name, String surname, String email, String password, double activeUsers, UserRole role) {
        super(name, surname, email, password);
        this.activeUsers = activeUsers;
    }

    public double getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(double activeUsers){
        this.activeUsers = activeUsers;
    }
}
