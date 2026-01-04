package it.uniroma2.dicii.ezgym.domain.model;

import java.util.UUID;

public class PersonalTrainer extends User {
    private double activeUsers;

    public PersonalTrainer() {
        // costruttore vuoto 
    }

    public PersonalTrainer(UUID id, String name, String surname, String email, String password, Role role, double activeUsers) {
        super(id, name, surname, email, password, role);
        this.activeUsers = activeUsers;
    }

    public double getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(double activeUsers){
        this.activeUsers = activeUsers;
    }
}
