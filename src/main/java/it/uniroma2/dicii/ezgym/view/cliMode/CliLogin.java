package it.uniroma2.dicii.ezgym.view.cliMode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;

public final class CliLogin {

    private CliLogin(){
    }

    public static UserBean startLogin() throws IOException, EmailAlreadyExistsException {

        System.out.println("\n===Login avviato in modalità CLI===");
        System.out.println("\nInserisci 0 per tornare indietro.");

        BufferedReader reader = InputReader.getInstance();
        LoginController loginController = new LoginController();
        UserBean loggedUser = null;

        while(true) {

            System.out.print("\nInserisci email: ");
            String email = reader.readLine();
            if (email == null) return null;
            email = email.trim();
            if (email.equals("0")) return null;

            System.out.print("\nInserisci Password: ");
            String password = reader.readLine();
            if (password == null) return null;
            password = password.trim();
            if (password.equals("0")) return null;

            if (email.isEmpty() || password.isEmpty()) {
                System.err.println("\nEmail e password sono obbligatori. Riprova.");
                continue;
            }

            try {
                UserBean credentials = new UserBean();
                credentials.setEmail(email);
                credentials.setPassword(password);

                loggedUser = loginController.login(credentials);

                System.out.println("\nLogin effettuato con successo!");
                System.out.println("Ciao " + loggedUser.getName() + " " + loggedUser.getSurname() + "!");
                return loggedUser;

            } catch (InvalidCredentialsException e) {
                System.err.println("\nErrore: " + e.getMessage());
                System.err.println("Riprova.");

            } catch (IllegalArgumentException e) {
                System.err.println("\nErrore: " + e.getMessage());
                System.err.println("Riprova.");

            } catch (Exception e) {
                System.err.println("\nErrore inatteso durante il login.");
                e.printStackTrace();
                System.err.println("Riprova.");
            }
        }
    }
}