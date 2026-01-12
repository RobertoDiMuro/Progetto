package it.uniroma2.dicii.ezgym.bean;

import java.util.regex.Pattern;

public abstract class AbstractUserDataBean {

    private String name;
    private String surname;
    private String email;
    private String password;

    protected static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    protected static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$");

    protected AbstractUserDataBean() {
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        requireNotBlank(name, "Il nome non può essere vuoto o nullo");
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        requireNotBlank(surname, "Il cognome non può essere vuoto o nullo");
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        requireNotBlank(email, "L'email non può essere vuota.");
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Formato email non valido.");
        }
        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        requireNotBlank(password, "La password non può essere vuota.");
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                "La password deve contenere almeno 8 caratteri, " +
                "una lettera maiuscola, una minuscola, un numero e un simbolo."
            );
        }
        this.password = password;
    }

    protected static void requireNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    private static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
