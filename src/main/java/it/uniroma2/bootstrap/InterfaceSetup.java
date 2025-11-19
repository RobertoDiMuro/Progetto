package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;

public final class InterfaceSetup {
    
    private InterfaceSetup(){
        //
    }

    private static final BufferedReader reader = InputReader.getInstance();
    
    public static InterfaceMode askInterfaceMode() throws IOException {
        System.out.println();
        System.out.println("Scegli la modalità grafica:");
        System.out.println("1. Modalità CLI");
        System.out.println("2. Modalità GUI");
        System.out.println();
        System.out.println("Inserisci il numero corrispondente alla modalità desiderata:");

        InterfaceMode interfaceMode = null;
        while (interfaceMode == null) {
            String input = reader.readLine().trim();
            switch (input) {
                case "1" -> interfaceMode = InterfaceMode.CLI;
                case "2" -> interfaceMode = InterfaceMode.GUI;
                default -> System.out.println("Input non valido. Per favore, inserisci 1 o 2:");
            }
        }

        System.out.println("\nModalità selezionata: " + interfaceMode);
        return interfaceMode;
    }

    
}
