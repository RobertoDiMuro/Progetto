package it.uniroma2.dicii.ezgym.domain.bean;

import java.util.regex.Pattern;

public class UserBean {
    
    private String name;
    private String surname;
    private String email;
    private String password;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$");

    public UserBean(){}

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Il nome non può essere vuoto o nullo");
        }
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        if(surname == null || surname.trim().isEmpty()){
            throw new IllegalArgumentException("Il cognome non può essere vuoto o nullo");
        }
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email non può essere vuota.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Formato email non valido.");
        }
        this.email = email.trim();
    }


    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La password non può essere vuota.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                "La password deve contenere almeno 8 caratteri, " +
                "una lettera maiuscola, una minuscola, un numero e un simbolo."
            );
        }
        this.password = password;
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }


}
