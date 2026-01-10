package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.SignupController;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;

public class CliSignup {
    
    private CliSignup() {
        //
    }

    public static UserBean startSignup() throws IOException, EmailAlreadyExistsException {
        System.out.println("\n===Registrazione avviata in modalità CLI===");
        System.out.println("\nInserisci 0 per tornare indietro.");

        BufferedReader reader = InputReader.getInstance();
        SignupController signupController = new SignupController();
        UserBean createdUser = null;

        while(true){

            System.out.print("Nome: ");
            String name = reader.readLine();
            if (name == null) return null;
            name = name.trim();
            if (name.equals("0")) return null;

            System.out.print("\nCognome: ");
            String surname = reader.readLine();
            if (surname == null) return null;
            surname = surname.trim();
            if (surname.equals("0")) return null;

            System.out.print("\nEmail: ");
            String email = reader.readLine();
            if (email == null) return null;
            email = email.trim();
            if (email.equals("0")) return null;

            System.out.print("\nPassword: ");
            String password = reader.readLine();
            if (password == null) return null;
            password = password.trim();
            if (password.equals("0")) return null;

            System.out.print("\nConferma Password: ");
            String confirm = reader.readLine();
            if (confirm == null) return null;
            confirm = confirm.trim();
            if (confirm.equals("0")) return null;

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                System.err.println("\nErrore: tutti i campi sono obbligatori. Riprova.");
                continue;
            }

            try {
                SignupBean bean = new SignupBean();
                bean.setName(name);
                bean.setSurname(surname);
                bean.setEmail(email);
                bean.setPassword(password);
                bean.setConfirmPw(confirm, password);

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

            } catch (Exception e) {
                System.err.println("\nErrore inatteso durante la registrazione.");
                e.printStackTrace();
                System.err.println("Riprova.");
            }
        }
    }
}