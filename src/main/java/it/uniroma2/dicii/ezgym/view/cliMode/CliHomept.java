package it.uniroma2.dicii.ezgym.view.cliMode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;

public class CliHomept {
    
    private static BufferedReader reader = InputReader.getInstance();

    private CliHomept() { 
        //
    }

    public static void startHomePT(UserBean userBean) throws IOException {
       
        PersonalTrainerBean pt = new PersonalTrainerBean();
        printWelcome(userBean);

        while (true) {
            System.out.println("\nCosa vuoi fare?");
            System.out.println("  1) Visualizza richieste");
            System.out.println("  2) Crea esercizi");
            System.out.println("  0) Esci");
            System.out.print("\n> ");

            String choice = reader.readLine().trim();
            switch(choice){
                case "1" -> {
                    CliRequestView.startRequestView(pt);
                }
                case "2" -> {
                    CliCreateExercise.startCreateExercise();
                }
                case "0" -> {
                    System.out.println("\nA presto!");
                    System.exit(0);
                }
                default -> System.err.println("Scelta non valida. Inserisci 1, 2 o 0.");
            }
        }

    }

    private static void printWelcome(UserBean userBean){
        System.out.println("\n====================================");
        System.out.println("              HOME (CLI)            ");
        System.out.println("====================================");
        System.out.println("Ciao coach " + userBean.getName() + "!");
    }
}
