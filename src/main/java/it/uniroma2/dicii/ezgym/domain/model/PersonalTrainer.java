package it.uniroma2.dicii.ezgym.domain.model;

import java.util.UUID;

public class PersonalTrainer extends User {
    private double activeUsers;

    public PersonalTrainer(UUID id, String name, String surname, String email, String password, double activeUsers) {
        super(id, name, surname, email, password);
        this.activeUsers = activeUsers;
    }

    public double getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(double activeUsers){
        this.activeUsers = activeUsers;
    }
}
