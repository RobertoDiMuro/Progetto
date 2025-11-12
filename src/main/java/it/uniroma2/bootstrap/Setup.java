package it.uniroma2.bootstrap;

import java.util.Scanner;

public final class Setup {

    private Setup(){
        //
    }

    private static Mode askMode(){

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=============================");
            System.out.println("     Benvenuto in EzGym!     ");
            System.out.println("=============================");
            System.out.println("                             ");
            System.out.println("  Avvio dell'applicazione... ");
            System.out.println("                             ");
            System.out.println("Scegli la mdodalità di utilizzo:");
            System.out.println("1. Modalità Database");
            System.out.println("2. Modalità Demo)");
            System.out.println("3. Modalità Filesystem");
            System.out.println("                             ");
            System.out.println("Inserisci il numero corrispondente alla modalità desiderata:");

            Mode mode = null;

            while(mode == null){
                String input = scanner.nextLine().trim();

                switch(input){
                    case "1":
                        mode = Mode.DATABASE;
                        break;
                    case "2":
                        mode = Mode.DEMO;
                        break;
                    case "3":
                        mode = Mode.FILESYSTEM;
                        break;
                    default:
                        System.out.println("Input non valido. Per favore, inserisci 1, 2 o 3:");
                }
            }
            System.out.println("                             ");
            System.out.println("Modalità selezionata: " + mode);

            return mode;
        }
    }
    
}
