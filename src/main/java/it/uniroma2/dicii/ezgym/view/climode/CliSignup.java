package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.SignupController;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public class CliSignup extends BaseCli {
    
    private static BufferedReader reader = InputReader.getInstance();


    private CliSignup() {
        //
    }

    public static UserBean startSignup() throws IOException, EmailAlreadyExistsException {
        System.out.println("\n===Registrazione avviata in modalità CLI===");
        System.out.println("\nInserisci 0 per tornare indietro.");

        SignupController signupController = new SignupController();
        UserBean createdUser = null;

        while(true){

            String name = readTrimmedInput("\nNome:");

            String surname = readTrimmedInput("\nCognome:");

            String email = readTrimmedInput("\nEmail: ");

            String password = readTrimmedInput("\nPassword: ");

           String confirmPw = readTrimmedInput("\nConferma Password: ");
           
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPw.isEmpty()) {
                System.err.println("\nErrore: tutti i campi sono obbligatori. Riprova.");
                continue;
            }

            try {
                SignupBean bean = new SignupBean();
                bean.setName(name);
                bean.setSurname(surname);
                bean.setEmail(email);
                bean.setPassword(password);
                bean.setConfirmPw(confirmPw, password);

                createdUser = signupController.signup(bean);

                System.out.println("\nRegistrazione avvenuta con successo!");
                System.out.println("Ciao " + createdUser.getName() + " " + createdUser.getSurname() + "!");
                return createdUser;

            } catch (EmailAlreadyExistsException e) {
                System.err.println("\nErrore: " + e.getMessage());
                System.err.println("Riprova.");

            } catch (IllegalArgumentException e) {
                System.err.println("\nErrore: " + e.getMessage());
                System.err.println("Riprova.");

            } catch (Exception _) {
                System.err.println("\nErrore inatteso durante la registrazione.");
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