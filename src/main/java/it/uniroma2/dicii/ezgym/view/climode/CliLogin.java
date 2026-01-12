package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public final class CliLogin extends BaseCli {

    private static BufferedReader reader = InputReader.getInstance();

    private CliLogin(){
    }

    public static UserBean startLogin() throws IOException {

        System.out.println("\n===Login avviato in modalità CLI===");
        System.out.println("\nInserisci 0 per tornare indietro.");

        LoginController loginController = new LoginController();
        UserBean loggedUser = null;

        while(true) {

            try {
                String email = readTrimmedInput("\nInserisci email: ");
                if (email == null) return null;

                String password = readTrimmedInput("\nInserisci Password: ");
                if (password == null) return null;

                if (email.isEmpty() || password.isEmpty()) {
                    System.err.println("\nEmail e password sono obbligatori. Riprova.");
                    continue;
                }

                UserBean credentials = new UserBean();
                credentials.setEmail(email);
                credentials.setPassword(password);

                loggedUser = loginController.login(credentials);

                System.out.println("\nLogin effettuato con successo!");
                System.out.println("Ciao " + loggedUser.getName() + " " + loggedUser.getSurname() + "!");
                return loggedUser;

            } catch (BackException _) {
                return null;

            } catch (InvalidCredentialsException | IllegalArgumentException e) {
                System.err.println("\nErrore: " + e.getMessage());
                System.err.println("Riprova.");

            } catch (Exception _) {
                System.err.println("\nErrore inatteso durante il login.");
                System.err.println("Riprova.");
            }
        }
    }

    private static String readTrimmedInput(String prompt) throws IOException {
        System.out.print(prompt);
        String input = reader.readLine();
        if (input == null) return null;
        input = input.trim();
        BaseCli.checkBackToHome(input);
        return input;
    }
}
