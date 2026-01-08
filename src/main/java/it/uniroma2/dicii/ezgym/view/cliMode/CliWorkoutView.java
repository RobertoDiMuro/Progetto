package it.uniroma2.dicii.ezgym.view.cliMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.dbms.ExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;


public final class CliWorkoutView {

    private CliWorkoutView() { }

    private static final Map<String, String> FOCUS_CACHE = new HashMap<>();

    public static void startWorkoutView(AthleteBean athleteBean, WorkoutBean workoutBean) throws IOException {
        BufferedReader reader = InputReader.getInstance();

        printHeader(athleteBean);

        if (workoutBean == null
                || workoutBean.getsSessions() == null
                || workoutBean.getsSessions().isEmpty()) {

            System.out.println("\nScheda non presente");
            waitBack(reader);
            return;
        }

        System.out.println("Ripeti per: " + workoutBean.getRepeteWeeks() + " settimane\n");

        for (WorkoutSessionBean session : workoutBean.getsSessions()) {
            renderSession(session);
        }

        waitBack(reader);
    }

    private static void renderSession(WorkoutSessionBean session) {
        if (session == null) return;

        System.out.println("=== " + safe(session.getDayOfWeek()) + " ===");

        List<SessionExerciseBean> exercises = session.getExercises();
        if (exercises == null) exercises = new ArrayList<>();

        List<SessionExerciseBean> sorted = new ArrayList<>(exercises);
        sorted.sort(Comparator.comparing(
                se -> getFocus(se != null ? se.getExerciseName() : null),
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        ));

        printTable(sorted);
        System.out.println();
    }

    private static void printTable(List<SessionExerciseBean> list) {
        String header = String.format(
                "%-25s %-15s %4s %4s %5s %-10s %s",
                "Esercizio", "Focus", "Sets", "Reps", "Rest", "Type", "Note"
        );
        System.out.println(header);
        System.out.println("-".repeat(Math.min(110, header.length() + 20)));

        for (SessionExerciseBean se : list) {
            if (se == null) continue;

            String ex = cut(safe(se.getExerciseName()), 25);
            String focus = cut(safe(getFocus(se.getExerciseName())), 15);

            String type = (se.getType() != null) ? se.getType().toString() : "";
            type = cut(type, 10);

            String notes = safe(se.getNotes());

            System.out.printf(
                    "%-25s %-15s %4d %4d %5.0f %-10s %s%n",
                    ex,
                    focus,
                    se.getSets(),
                    se.getReps(),
                    se.getRestTime(),
                    type,
                    notes
            );
        }
    }

    private static String getFocus(String exerciseName) {
        if (exerciseName == null || exerciseName.isBlank()) return "";

        String cached = FOCUS_CACHE.get(exerciseName);
        if (cached != null) return cached;

        String focus = loadFocusSafe(exerciseName);
        FOCUS_CACHE.put(exerciseName, focus);
        return focus;
    }

    
    private static String loadFocusSafe(String exerciseName) {
        try {
            Exercise ex = ExerciseDbmsDao.getInstance().findBy(exerciseName);
            if (ex != null && ex.getFocus() != null) return ex.getFocus();
        } catch (RuntimeException ignored) {
        }
        return "";
    }

    private static void printHeader(AthleteBean athleteBean) {
        System.out.println("\n====================================");
        System.out.println("        SCHEDA ALLENAMENTO (CLI)    ");
        System.out.println("====================================");
        if (athleteBean != null) {
            System.out.println("Atleta: " + safe(athleteBean.getName()) + " " + safe(athleteBean.getSurname()));
        }
        System.out.println();
    }

    private static void waitBack(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("0) Indietro");
            System.out.print("> ");
            String choice = reader.readLine();
            if (choice == null) return;
            choice = choice.trim();
            if ("0".equals(choice)) return;
            System.err.println("Scelta non valida. Inserisci 0 per tornare indietro.");
        }
    }

    private static String safe(String s) {
        return (s == null) ? "" : s;
    }

    private static String cut(String s, int max) {
        if (s == null) return "";
        if (s.length() <= max) return s;
        return s.substring(0, Math.max(0, max - 1)) + "…";
    }
}
