package it.uniroma2.dicii.ezgym.exceptions;

public class PersistenceException extends RuntimeException{
    public PersistenceException(String message){ super(message);}
    public PersistenceException(String message, Throwable cause){ super(message, cause);}
    public PersistenceException(Throwable cause){ super(cause);}
}