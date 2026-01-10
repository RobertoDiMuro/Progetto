package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class InputReader {

    private static BufferedReader instance;

    private InputReader() {
        //
    }

    public static BufferedReader getInstance() {
        if (instance == null) {
            instance = new BufferedReader(new InputStreamReader(System.in));
        }
        return instance;
    }

    public static String readLine() {
        try {
            return getInstance().readLine();
        } catch (IOException _) {
            System.err.println("Errore nella lettura dell'input");
            return null;
        }
    }
}
