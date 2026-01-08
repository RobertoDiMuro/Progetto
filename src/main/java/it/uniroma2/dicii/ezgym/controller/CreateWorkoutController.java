package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class CreateWorkoutController {
    
    private final WorkoutDao dao;

    public CreateWorkoutController() {
        DaoFactory factory = DaoFactory.getInstance();
        this.dao = factory.createWorkoutDao();
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("Errore durante il salvataggio della scheda");
        }
    }
}