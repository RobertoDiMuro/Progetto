package it.uniroma2.dicii.ezgym.bean;

public class PersonalTrainerBean extends UserBean {

    private double activeUsers;

    public PersonalTrainerBean(){}

    public double getActiveUsers(){
        return activeUsers;
    }

    public void setActiveUsers(double activeUsers){
        if(activeUsers < 0){
            throw new IllegalArgumentException("Il numero di utenti attivi deve essere maggiore o uguale a zero");
        }
        this.activeUsers = activeUsers;
    }
}
