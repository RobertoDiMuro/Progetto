package it.uniroma2.dicii.ezgym.view.cliMode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;

public class CliHome {
    
    private CliHome() {
        //
    }

    public static void startHome(UserBean userBean) throws IOException {
        BufferedReader reader = InputReader.getInstance();
        AthleteBean currAthlete = new AthleteBean();
        WorkoutBean wokoutBean = new WorkoutBean();
        fillAthleteBeanField(userBean, currAthlete);
        
        printWelcome(userBean);

         while (true) {
            System.out.println("\nCosa vuoi fare?");
            System.out.println("  1) Richiedi scheda di allenamento");
            System.out.println("  2) Richiedi dieta");
            System.out.println("  3) Visualizza la tua scheda");
            System.out.println("  4) Visualizza la tua dieta");
            System.out.println("  0) Esci");
            System.out.print("\n> ");

            String choice = reader.readLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.println("\nAvvio procedura di richiesta scheda di allenamento...");
                    CliWorkoutRequest.startWorkoutRequest(currAthlete);
                }
                case "2" -> {
                    System.out.println("\nFunzionalità non ancora implementata.");
                }
                case "3" ->{
                    CliWorkoutView.startWorkoutView(currAthlete, wokoutBean);
                }
                case "4" ->{
                    System.out.println("\nFunzionalità non ancora implementata.");
                
                }
                case "0" -> {
                    System.out.println("\nA presto!");
                    System.exit(0);
                }
                default -> System.err.println("Scelta non valida. Inserisci 1, 2 o 0.");
            }

        }

    }

    private static void printWelcome(UserBean userBean) {
        System.out.println("\n====================================");
        System.out.println("              HOME (CLI)            ");
        System.out.println("====================================");
        System.out.println("Ciao " + userBean.getName() + " " + userBean.getSurname() + "!");
    }

    private static void fillAthleteBeanField(UserBean userBean, AthleteBean athleteBean) {
        athleteBean.setId(userBean.getId());
        athleteBean.setName(userBean.getName());
        athleteBean.setSurname(userBean.getSurname());
        athleteBean.setEmail(userBean.getEmail());
    }
}
