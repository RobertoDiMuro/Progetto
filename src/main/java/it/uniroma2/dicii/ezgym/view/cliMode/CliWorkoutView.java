package it.uniroma2.dicii.ezgym.view.cliMode;

import it.uniroma2.bootstrap.InputReader;
import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.controller.WorkoutController;
import it.uniroma2.dicii.ezgym.exceptions.BackException;
import it.uniroma2.dicii.ezgym.utils.BaseCli;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class CliWorkoutView {

    private CliWorkoutView() { }

    public static void startWorkoutView(AthleteBean athleteBean, WorkoutBean workoutBean) throws IOException {
        BufferedReader reader = InputReader.getInstance();

        System.out.println("\n====================================");
        System.out.println("         LA TUA SCHEDA (CLI)        ");
        System.out.println("====================================");
        System.out.println("Inserisci 0 in qualunque momento per tornare alla Home.");


        try {
            WorkoutController controller = new WorkoutController();
            WorkoutBean found = controller.getWorkoutByAthleteName(athleteBean);

            if (found == null) {
                System.out.println("Nessuna scheda presente per questo atleta.");
                waitBackToHome(reader);
                return;
            }

            if (workoutBean != null) {
                workoutBean.setWorkoutId(found.getWorkoutId());
                workoutBean.setAthleteId(found.getAthleteId());
                workoutBean.setRepeteWeeks(found.getRepeteWeeks());
                workoutBean.setSessions(found.getsSessions());
            }

            System.out.println("Atleta: " + athleteBean.getName() + " " + athleteBean.getSurname());
            System.out.println("Settimane da ripetere: " + found.getRepeteWeeks());

            List<WorkoutSessionBean> sessions = found.getsSessions();
            if (sessions == null || sessions.isEmpty()) {
                System.out.println("\nLa scheda non ha sessioni salvate.");
                waitBackToHome(reader);
                return;
            }

            for (WorkoutSessionBean s : sessions) {
                System.out.println("\n---" + s.getDayOfWeek() + " ---");

                List<SessionExerciseBean> exs = s.getExercises();
                if (exs == null || exs.isEmpty()) {
                    System.out.println("(Nessun esercizio)");
                    continue;
                }

                for (SessionExerciseBean ex : exs) {
                    System.out.println("• " + ex.getExerciseName()
                            + " | " + ex.getSets() + "x" + ex.getReps()
                            + " | Recupero: " + ex.getRestTime() + "s"
                            + " | Tipo: " + ex.getType()
                            + (ex.getNotes() != null && !ex.getNotes().isBlank() ? " | Note: " + ex.getNotes() : "")
                    );
                }
            }

            waitBackToHome(reader);

        } catch (BackException e) {
            return;
        } catch (RuntimeException e) {
            System.err.println("Errore nel recupero della scheda: " + e.getMessage());
            waitBackToHome(reader);
        }
    }

    private static void waitBackToHome(BufferedReader reader) throws IOException {
        System.out.println("\nInserisci 0 per tornare indietro.");
        while (true) {
            String input = reader.readLine();
            BaseCli.checkBackToHome(input); 
            System.out.println("Scelta non valida. Inserisci 0 per tornare indietro.");
        }
    }
}
