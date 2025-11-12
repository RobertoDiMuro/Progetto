package it.uniroma2.bootstrap;

import java.util.Scanner;

public final class InterfaceSetup {
    
    private InterfaceSetup(){
        //
    }

    private static InterfaceMode askInterfaceMode(){

        try(Scanner scanner = new Scanner(System.in)){
            System.out.println();
            System.out.println("Scegli la mdodalità grafica:");
            System.out.println("1. Modalità CLI");
            System.out.println("2. Modalità GUI");
            System.out.println("                             ");
            System.out.println("Inserisci il numero corrispondente alla modalità desiderata:");

            InterfaceMode interfaceMode = null;

            while(interfaceMode == null){
                String input = scanner.nextLine().trim();

                switch(input){
                    case "1":
                        interfaceMode = InterfaceMode.CLI;
                        break;
                    case "2":
                        interfaceMode = InterfaceMode.GUI;
                        break;
                    default:
                        System.out.println("Input non valido. Per favore, inserisci 1 o 2:");
                }
                
            }
            System.out.println("                             ");
            System.out.println("Modalità selezionata: " + interfaceMode);
                
            return interfaceMode;
        }
        
    }
    
}
