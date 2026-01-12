package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutDao;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class CreateWorkoutController {
    
    private final WorkoutDao dao;

    public CreateWorkoutController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.dao = factory.createWorkoutDao();
    }

    public void saveWorkout(UUID athleteId, int repeteWeeks, WorkoutSessionBean[] sessions, List<SessionExerciseBean>[] dayExercises){
        WorkoutBean workoutBean = new WorkoutBean();
        workoutBean.setAthleteId(athleteId);
        workoutBean.setRepeteWeeks(repeteWeeks);

        List<WorkoutSessionBean> sessionBeans = new ArrayList<>();
        for (WorkoutSessionBean s : sessions) {
            if (s != null) {
                if (s.getSessionId() <= 0) {
                    throw new IllegalArgumentException("Errore: sessione non inizializzata (sessionId <= 0).");
                }
                sessionBeans.add(s);
            }
        }

        if (sessionBeans.isEmpty()) {
            throw new IllegalArgumentException("Aggiungi almeno una sessione.");
        }

        boolean hasAtLeastOneExercise = false;
        for (List<SessionExerciseBean> list : dayExercises) {
            if (list != null && !list.isEmpty()) {
                hasAtLeastOneExercise = true;
                break;
            }
        }

        if (!hasAtLeastOneExercise) {
            throw new IllegalArgumentException("Aggiungi almeno un esercizio prima di salvare la scheda.");
        }

        workoutBean.setSessions(sessionBeans);
        saveWorkout(workoutBean);
    }


    public void saveWorkout(WorkoutBean bean) {
        try {
            List<WorkoutSession> sessions = new ArrayList<>();

            for (WorkoutSessionBean sb : bean.getsSessions()) {
                sessions.add(new WorkoutSession(
                        sb.getSessionId(),
                        sb.getDayOfWeek(),
                        new ArrayList<>()
                ));
            }

            Workout workout = new Workout(0, bean.getAthleteId(), bean.getRepeteWeeks(), sessions);

            dao.insert(workout, workout.getWorkoutId());

            bean.setWorkoutId(workout.getWorkoutId());

        } catch (Exception _) {
            throw new PersistenceException("Errore durante il salvataggio della scheda");
        }
    }
}