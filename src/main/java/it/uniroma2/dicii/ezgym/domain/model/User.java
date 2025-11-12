package it.uniroma2.dicii.ezgym.domain.model;

import java.util.UUID;

public abstract class User {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;

    protected User() {}
    
    protected User(UUID id, String name, String surname, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public UUID getID(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setID(UUID id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }


}
