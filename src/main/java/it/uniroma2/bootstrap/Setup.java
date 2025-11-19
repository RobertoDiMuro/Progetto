package it.uniroma2.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;

public final class Setup {

    private Setup(){
        //
    }

    private static final BufferedReader reader = InputReader.getInstance();

    public static Mode askMode() throws IOException {
        System.out.println("=============================");
        System.out.println("     Benvenuto in EzGym!     ");
        System.out.println("=============================");
        System.out.println();
        System.out.println("  Avvio dell'applicazione... ");
        System.out.println();
        System.out.println("Scegli la modalità di utilizzo:");
        System.out.println("1. Modalità Database");
        System.out.println("2. Modalità Demo");
        System.out.println("3. Modalità Filesystem");
        System.out.println();
        System.out.println("Inserisci il numero corrispondente alla modalità desiderata:");

        Mode mode = null;
        while (mode == null) {
            String input = reader.readLine().trim();
            switch (input) {
                case "1" -> mode = Mode.DATABASE;
                case "2" -> mode = Mode.DEMO;
                case "3" -> mode = Mode.FILESYSTEM;
                default -> System.out.println("Input non valido. Per favore, inserisci 1, 2 o 3:");
            }
        }

        System.out.println("\nModalità selezionata: " + mode);
        return mode;
}

    
}
