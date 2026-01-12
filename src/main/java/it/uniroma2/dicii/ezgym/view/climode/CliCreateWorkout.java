package it.uniroma2.dicii.ezgym.view.climode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.ExerciseBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.controller.AddExerciseToSessionController;
import it.uniroma2.dicii.ezgym.controller.CreateSessionController;
import it.uniroma2.dicii.ezgym.controller.CreateWorkoutController;
import it.uniroma2.dicii.ezgym.controller.ExerciseController;
import it.uniroma2.dicii.ezgym.controller.PtRequestcontroller;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

public class CliCreateWorkout extends BaseCli {

    private static BufferedReader reader = InputReader.getInstance();

    private CliCreateWorkout() {
        //
    }

    private static final String[] DAY_LABELS = {
            "Primo giorno",
            "Secondo giorno",
            "Terzo giorno",
            "Quarto giorno",
            "Quinto giorno",
            "Sesto giorno"
    };

    public static void startCreateWorkout(AthleteBean athlete) throws IOException {
        
        PtRequestcontroller ptRequestcontroller = new PtRequestcontroller();
        CreateWorkoutController createWorkoutController = new CreateWorkoutController();
        CreateSessionController createSessionController = new CreateSessionController();
        AddExerciseToSessionController addExerciseController = new AddExerciseToSessionController();
        ExerciseController exerciseController = new ExerciseController();

        WorkoutSessionBean[] sessions = new WorkoutSessionBean[6];
        @SuppressWarnings("unchecked")
        List<SessionExerciseBean>[] dayExercises = new ArrayList[6];
        int[] exerciseCounters = new int[6];
        for (int i = 0; i < 6; i++) {
            dayExercises[i] = new ArrayList<>();
            exerciseCounters[i] = 0;
        }

        int selectedDayIndex = 0;
        try {
            System.out.println("\n====================================");
            System.out.println("         CREA SCHEDA (CLI)         ");
            System.out.println("====================================");
            System.out.println("Atleta: " + athlete.getName() + " " + athlete.getSurname());
            System.out.println("Inserisci 0 in qualunque momento per tornare indietro.\n");

            int repeteWeeks = repeteWeeksRequest();

            ensureSessionCreated(createSessionController, sessions, selectedDayIndex);

            

            while (true) {
                System.out.println("\n------------------------------------");
                System.out.println("Giorno selezionato: " + DAY_LABELS[selectedDayIndex]);
                System.out.println("Esercizi in questo giorno: " + dayExercises[selectedDayIndex].size());
                System.out.println("------------------------------------");

                printDaysStatus(sessions, dayExercises);

                System.out.println("\nCosa vuoi fare?");
                System.out.println("  1) Cambia giorno");
                System.out.println("  2) Aggiungi esercizio al giorno corrente");
                System.out.println("  3) Mostra esercizi del giorno corrente");
                System.out.println("  4) Salva scheda");
                System.out.println("  0) Indietro");
                System.out.print("\n> ");

                String choice = reader.readLine().trim();
                checkBackToHome(choice);
                switch(choice){
                    case "1" -> {
                        selectedDayIndex = selectDay();
                        ensureSessionCreated(createSessionController, sessions, selectedDayIndex);
                    }
                    case "2" -> {
                        ensureSessionCreated(createSessionController, sessions, selectedDayIndex);
                        addExerciseFlow(exerciseController, addExerciseController, sessions[selectedDayIndex], dayExercises[selectedDayIndex], exerciseCounters, selectedDayIndex);
                    }
                    case "3" ->  printDayExercises(selectedDayIndex, dayExercises[selectedDayIndex]);
                    case "4" -> {
                        if (trySaveWorkout(athlete, repeteWeeks, sessions, dayExercises, createWorkoutController, ptRequestcontroller)) {
                            return;
                        }
                    }default -> System.err.println("Scelta non valida. Inserisci 1, 2, 3, 4 o 0.");
                }
            }

        }catch (BackException _) {
            //
        }
    }

    private static boolean trySaveWorkout(
            AthleteBean athlete,
            int repeteWeeks,
            WorkoutSessionBean[] sessions,
            List<SessionExerciseBean>[] dayExercises,
            CreateWorkoutController createWorkoutController,
            PtRequestcontroller ptRequestcontroller) {

        try {
            saveWorkout(athlete, repeteWeeks, sessions, dayExercises, createWorkoutController, ptRequestcontroller);
            System.out.println("\nScheda creata con successo! Richiesta chiusa.");
            return true;
        } catch (RuntimeException e) {
            System.err.println("\nImpossibile salvare la scheda: " + e.getMessage());
            System.out.println("\nInserisci 0 per tornare indietro (o continua per modificare la scheda).");
            return false;
        }
    }


    private static int repeteWeeksRequest() throws IOException {
        while (true) {
            System.out.print("Per quante settimane deve essere ripetuta la scheda di allenamento? > ");
            String input = reader.readLine().trim();
            try {
                checkBackToHome(input);
            } catch (Exception _) {
                return 0;
            }
            try {
                int weeks = Integer.parseInt(input);
                if (weeks < 1) {
                    System.err.println("Inserisci un numero valido di settimane (minimo 1).");
                    continue;
                }
                return weeks;
            } catch (NumberFormatException _) {
                System.err.println("Input non valido. Inserisci un numero intero.");
            }
        }
    }

    private static void ensureSessionCreated(CreateSessionController controller, WorkoutSessionBean[] sessions, int index){

        if (sessions[index] != null) return;

        WorkoutSessionBean bean = new WorkoutSessionBean();
        bean.setDayOfWeek(DAY_LABELS[index]);
        controller.createEmptySession(bean);

        sessions[index] = bean;
    }
    
    private static void printDaysStatus(WorkoutSessionBean[] sessions, List<SessionExerciseBean>[] dayExercises){
        System.out.println("\nStato giorni:");
        for (int i = 0; i < 6; i++) {
            String created = (sessions[i] != null) ? "SI" : "NO";
            int count = (dayExercises[i] == null) ? 0 : dayExercises[i].size();
            System.out.println("  - " + DAY_LABELS[i] + " | Sessione creata: " + created + " | Esercizi: " + count);
        }
    }

    private static int selectDay() throws IOException{
        System.out.println("\nSeleziona giorno:");
        for (int i = 0; i < 6; i++) {
            System.out.println("  " + (i + 1) + ") " + DAY_LABELS[i]);
        }
        return askIndex("\n> ", 1, 6) - 1;
    }

     private static int askIndex(String label, int min, int max) throws IOException {
        while (true) {
            System.out.print(label);
            String input = reader.readLine();
            if (input == null) input = "";
            input = input.trim();

            checkBackToHome(input);
            try {
                int v = Integer.parseInt(input);
                if (v < min || v > max) {
                    System.err.println("Inserisci un numero tra " + min + " e " + max + ".");
                } else {
                    return v;
                }
            } catch (NumberFormatException _) {
                System.err.println("Inserisci un numero valido.");
            }
        }
    }

    private static void addExerciseFlow(ExerciseController exerciseController, AddExerciseToSessionController addExerciseController, WorkoutSessionBean sessionBean, List<SessionExerciseBean> localList, int[] counters, int dayIndex) throws IOException {

        if (sessionBean == null || sessionBean.getSessionId() <= 0) {
            System.err.println("Errore: sessione non inizializzata correttamente.");
            return;
        }

        List<ExerciseBean> all;
        try {
            all = exerciseController.getAllExercises();
        } catch (PersistenceException _) {
            System.err.println("Errore nel caricamento degli esercizi: ");
            return;
        }

        if (all == null || all.isEmpty()) {
            System.out.println("\n(Nessun esercizio presente!)");
            return;
        }
        
        System.out.println("\n=== Aggiungi esercizio (" + DAY_LABELS[dayIndex] + ") ===");
        System.out.println("Inserisci 0 per tornare indietro.\n");

        for (int i = 0; i < all.size(); i++) {
            ExerciseBean e = all.get(i);
            System.out.println("  " + (i + 1) + ") " + safe(e.getName()) + " | Focus: " + safe(e.getFocus()));
        }

        int exIndex = askIndex("\nScegli esercizio (numero): ", 1, all.size());
        ExerciseBean chosen = all.get(exIndex - 1);

        int sets = askInt("Serie (sets): ");
        int reps = askInt("Ripetizioni (reps): ");
        double restTime = askDouble("Recupero (secondi): ");

        ExerciseType type = askType();

        System.out.print("Note (opzionale, invio per saltare): ");
        String notes = reader.readLine();
        if (notes == null) notes = "";
        notes = notes.trim();
        checkBackToHome(notes);

        SessionExerciseBean bean = new SessionExerciseBean();
        bean.setSessionId(sessionBean.getSessionId());
        bean.setExerciseName(chosen.getName());
        bean.setSets(sets);
        bean.setReps(reps);
        bean.setRestTime(restTime);
        bean.setType(type);
        bean.setNotes(notes);

        addExerciseController.addExerciseToSession(bean);
        localList.add(bean);
        counters[dayIndex]++;
        System.out.println("\nEsercizio aggiunto! (Totale oggi: " + localList.size() + ")");
    }

    private static int askInt(String label) throws IOException {
        while (true) {
            System.out.print(label);
            String input = reader.readLine();
            if (input == null) input = "";
            input = input.trim();

            checkBackToHome(input);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException _) {
                System.err.println("Inserisci un numero valido.");
            }
        }
    }

    private static double askDouble(String label) throws IOException {
        while (true) {
            System.out.print(label);
            String input = reader.readLine();
            if (input == null) input = "";
            input = input.trim();

            checkBackToHome(input);

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException _) {
                System.err.println("Inserisci un numero valido (es: 60 oppure 60.5).");
            }
        }
    }

    private static ExerciseType askType() throws IOException {
        ExerciseType[] values = ExerciseType.values();

        System.out.println("\nTipo esercizio:");
        for (int i = 0; i < values.length; i++) {
            System.out.println("  " + (i + 1) + ") " + values[i]);
        }

        int idx = askIndex("Seleziona tipo (numero): ", 1, values.length);
        return values[idx - 1];
    }

    private static void printDayExercises(int dayIndex, List<SessionExerciseBean> list) {
        System.out.println("\n=== " + DAY_LABELS[dayIndex] + " ===");
        if (list == null || list.isEmpty()) {
            System.out.println("(Nessun esercizio)");
            return;
        }
        int i = 1;
        for (SessionExerciseBean ex : list) {
            System.out.println("  " + (i++) + ") " + ex.getExerciseName()
                    + " | " + ex.getSets() + "x" + ex.getReps()
                    + " | Recupero: " + ex.getRestTime() + "s"
                    + " | Tipo: " + ex.getType()
                    + (ex.getNotes() != null && !ex.getNotes().isBlank() ? " | Note: " + ex.getNotes() : ""));
        }
    }

    private static void saveWorkout(AthleteBean athlete, int repeteWeeks, WorkoutSessionBean[] sessions, List<SessionExerciseBean>[] dayExercises, CreateWorkoutController createWorkoutController, PtRequestcontroller ptRequestcontroller) {

        createWorkoutController.saveWorkout(athlete.getId(), repeteWeeks, sessions, dayExercises);
        ptRequestcontroller.closeRequest(athlete.getId());
    }

}
