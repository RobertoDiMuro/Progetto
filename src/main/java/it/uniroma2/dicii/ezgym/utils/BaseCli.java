package it.uniroma2.dicii.ezgym.utils;

import it.uniroma2.dicii.ezgym.exceptions.BackException;

public abstract class BaseCli {

    protected BaseCli() {
        //
    }

    public static void checkBackToHome(String input) {
        if ("0".equals(input)) {
            throw new BackException();
        }
    }

    public static String safe(String s) {
        return s == null ? "" : s;
    }
}
