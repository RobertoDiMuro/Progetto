package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;


import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.bootstrap.Mode;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;

public final class CliStart {

    private CliStart() { }

    public static void start(Mode mode) throws IOException, EmailAlreadyExistsException {
        BufferedReader reader = InputReader.getInstance();

        printWelcome();

        while (true) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("  1) Login");
            System.out.println("  2) Registrazione");
            System.out.println("  0) Esci");
            System.out.print("\n> ");

            String choice = reader.readLine();
            if (choice == null) {
                System.out.println("\nA presto!");
                System.exit(0);
            }
            choice = choice.trim();

            switch (choice) {
                case "1" -> {
                    UserBean user = CliLogin.startLogin();
                    if (user != null) {
                        Role role = user.getRole();
                        switch (role) {
                            case ATHLETE:
                                CliHome.startHome(user);
                                break;
                            case PERSONAL_TRAINER:
                                CliHomept.startHomePT(user);
                                break;
                            default:
                                CliHome.startHome(user);
                                break;
                        }
                    }
                }
                case "2" -> {
                    UserBean user = CliSignup.startSignup();
                    if (user != null) {
                        CliHome.startHome(user);
                        return;
                    }
                }
                case "0" -> {
                    System.out.println("\nA presto!");
                    System.exit(0); 
                }
                default -> System.err.println("Scelta non valida. Inserisci 1, 2 o 0.");
            }
        }
    }

    private static void printWelcome() {
        System.out.println("\n====================================");
        System.out.println("      Benvenuto in EZGym (CLI)      ");
        System.out.println("====================================");
    }
}
