package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.controller.PtRequestcontroller;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public class CliRequestView extends BaseCli {

    private static BufferedReader reader = InputReader.getInstance();

    private CliRequestView() {
        //
    }

    public static void startRequestView() throws IOException {

        PtRequestcontroller controller = new PtRequestcontroller();

        while (true) {
            System.out.println("\n====================================");
            System.out.println("     RICHIESTE ATLETI (PT - CLI)    ");
            System.out.println("====================================");
            System.out.println("Inserisci 0 per tornare indietro.\n");

            List<AthleteBean> requests;
            try {
                requests = controller.getAthletesRequest();
            } catch (Exception e) {
                System.err.println("Errore nel caricamento delle richieste: " + e.getMessage());
                waitBack(); 
                return;
            }

            if (requests == null || requests.isEmpty()) {
                System.out.println("(Nessuna richiesta di scheda presente)");
                waitBack();
                return;
            }

            printRequests(requests);

            System.out.println("\nSeleziona un atleta inserendo il NUMERO (oppure 0 per tornare indietro).");
            System.out.print("> ");
            String input = reader.readLine().trim();

            try {
                checkBackToHome(input);
            } catch (BackException _) {
                return; 
            }

            int index = parseIndex(input);
            if (index < 1 || index > requests.size()) {
                System.err.println("Scelta non valida.");
                continue;
            }

            AthleteBean selected = requests.get(index - 1);
            try {
                athleteActionsLoop(selected);
            } catch (BackException _) {
                //
            }
        }
    }

    private static void athleteActionsLoop(AthleteBean athlete) throws IOException {

        while (true) {
            System.out.println("\n------------------------------------");
            System.out.println("Atleta selezionato: " + safe(athlete.getName()) + " " + safe(athlete.getSurname()));
            System.out.println("------------------------------------");

            printAthleteSummary(athlete);

            System.out.println("\nCosa vuoi fare?");
            System.out.println("  1) Vedi tutti i dati dell'atleta");
            System.out.println("  2) Crea scheda di allenamento");
            System.out.println("  3) Crea dieta");
            System.out.println("  0) Indietro");
            System.out.print("\n> ");

            String choice = reader.readLine().trim();
            checkBackToHome(choice);
            switch(choice){
                case "1" -> {
                    System.out.println("\n=== DATI ATLETA ===");
                    printAllAthleteData(athlete);
                    System.out.println("\n(0 per tornare indietro)");
                    waitBack();
                }
                case "2" -> {
                    System.out.println("\nAvvio procedura di creazione scheda di allenamento...");
                    CliCreateWorkout.startCreateWorkout(athlete);
                }
                case "3" -> {
                    System.out.println("Funzionalità non ancora implementata.");
                    System.out.println("(0 per tornare indietro)");
                    waitBack();
                }
                default -> System.err.println("Scelta non valida. Inserisci 1, 2, 3 o 0.");
            }
        }
    }

    private static void printRequests(List<AthleteBean> requests) {
        System.out.println("Richieste di scheda: " + requests.size());
        System.out.println();

        int i = 1;
        for (AthleteBean a : requests) {
            String fullName = safe(a.getName()) + " " + safe(a.getSurname());
            String target = a.getTarget().toString();
            String weight = String.valueOf(a.getWeight());
            String bmi = getBmi(a);
            System.out.println("  " + (i++) + ") " + fullName
                    + " | Peso: " + (weight.isBlank() ? "N/A" : weight + " kg")
                    + " | BMI: " + bmi
                    + " | Obiettivo: " + (target.isBlank() ? "N/A" : target));
        }
    }

    private static void printAthleteSummary(AthleteBean a) {
        String target = a.getTarget().toString();
        String weight = String.valueOf(a.getWeight());
        String bmi = getBmi(a);


        if (!weight.isBlank()) System.out.println("Peso: " + weight + " kg");
        if (!bmi.isBlank()) System.out.println("BMI: " + bmi);
        if (!target.isBlank()) System.out.println("Obiettivo: " + target);
        System.out.println("Richiesta: Scheda (recente)");
    }

    private static void printAllAthleteData(AthleteBean a) {
        System.out.println("Nome: " + safe(a.getName()));
        System.out.println("Cognome: " + safe(a.getSurname()));
        System.out.println("Email: " + safe(a.getEmail()));
        System.out.println("Genere: " + safe(a.getGender()));
        System.out.println("Età: " + a.getAge() + " anni");
        System.out.println("Peso: " + a.getWeight() + " kg");
        System.out.println("Altezza: " + a.getHeight() + " cm");
        System.out.println("Obiettivo: " +a.getTarget());
        System.out.println("Livello di attività: " + a.getActivityLevel());
        System.out.println("Giorni di allenamento settimanali: " + a.getWorkoutDay());
        System.out.println("BMI: " + getBmi(a));
        System.out.println("Richiesta: Scheda (recente)");
    }

    private static String getBmi(AthleteBean a) {
        double heightMeters = a.getHeight() / 100.0;
        return String.valueOf(Math.round(a.getWeight() / (heightMeters * heightMeters) * 100.0) / 100.0);
    }
    
    private static void waitBack() throws IOException {
        while (true) {
            String input = reader.readLine();
            checkBackToHome(input);
            System.out.println("Inserisci 0 per tornare indietro.");
        }
    }

    private static int parseIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception _) {
            return -1;
        }
    }

}
