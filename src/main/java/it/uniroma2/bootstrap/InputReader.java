package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class InputReader {

    private static final BufferedReader instance = null;

    private InputReader(){
        //
    }

    public static BufferedReader getInstance(){
        if(instance == null){
            return new BufferedReader(new InputStreamReader(System.in));
        }
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
