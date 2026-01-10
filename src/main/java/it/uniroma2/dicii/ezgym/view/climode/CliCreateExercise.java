package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.ExerciseBean;
import it.uniroma2.dicii.ezgym.controller.ExerciseController;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public class CliCreateExercise extends BaseCli {
    
    private CliCreateExercise() {
        //
    }

    public static void startCreateExercise() throws IOException {
        
        BufferedReader reader = InputReader.getInstance();
        ExerciseController exerciseController = new ExerciseController();

        while(true){
            printWelcome();

            printExercises(exerciseController);

            System.out.println("\nCosa vuoi fare?");
            System.out.println("  1) Crea nuovo esercizio");
            System.out.println("  2) Ricarica / Visualizza esercizi");
            System.out.println("  0) Indietro");
            System.out.print("\n> ");

            String choice = reader.readLine().trim();
            try {
                checkBackToHome(choice);
            } catch (BackException e) {
                return;
            }
            switch(choice){
                case "1" -> {
                    try {
                        createExerciseFlow(reader, exerciseController);
                    } catch (BackException e) {}
                }
                case "2" -> {

                }
                default -> System.err.println("Scelta non valida. Inserisci 1, 2 o 0.");
            }
        }
    }

    private static void printExercises(ExerciseController exerciseController){
        try{
            List<ExerciseBean> exercises = exerciseController.getAllExercises();

            if (exercises == null || exercises.isEmpty()) {
                System.out.println("(Nessun esercizio presente)");
                return;
            }

            System.out.println("Esercizi presenti:");
            int i = 1;
            for (ExerciseBean e : exercises) {
                System.out.println("  " + (i++) + ") " + safe(e.getName()) + " | Focus: " + safe(e.getFocus()));
            }
        }catch (PersistenceException ex) {
            System.err.println("Errore nel caricamento degli esercizi: " + ex.getMessage());
        }
    }

    private static void createExerciseFlow(BufferedReader reader, ExerciseController controller) throws IOException {
        System.out.println("\n=== Creazione esercizio ===");
        System.out.println("Inserisci 0 per tornare indietro.\n");
        ExerciseBean newExercise = new ExerciseBean();

        while(true){
            System.out.println("\nNome esercizio:");
            try{
                String name = reader.readLine().trim();
                if(name.isEmpty()) continue;
                if(name.equals("0")){
                    checkBackToHome(name);
                }
                newExercise.setName(name);
            }catch(BackException e){
                throw e;
            }
            System.out.println("\nFocus:");
            try{
                String focus = reader.readLine().trim();
                if(focus.isEmpty()) continue;
                if(focus.equals("0")){
                    BaseCli.checkBackToHome(focus);
                }
                newExercise.setFocus(focus);
            }catch(BackException e){
                throw e;
            }
            controller.createExercise(newExercise);
            System.out.println("\nEsercizio creato con successo!");
            return;
        }
    }

    private static void printWelcome(){
        System.out.println("\n====================================");
        System.out.println("         I tuoi esercizi (CLI)        ");
        System.out.println("====================================");
        System.out.println("Inserisci 0 in qualunque momento per tornare alla Home.");
    }
}
