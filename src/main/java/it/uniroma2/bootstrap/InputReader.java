package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class InputReader {

    private static final BufferedReader instance = new BufferedReader(new InputStreamReader(System.in));

    private InputReader(){
        //
    }

    public static BufferedReader getInstance(){
        return instance;
    }

    public static String readLine(){
        try{
            return instance.readLine();
        }catch(IOException e){
            System.err.println("Errore nella lettura dell'input");
            return null;
        }
    }
}
