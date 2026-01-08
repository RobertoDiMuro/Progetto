package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.dbms.SessionExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public class WorkoutController {

    private final WorkoutDao dao;
    
    public WorkoutController() {
        this.dao = DaoFactory.getInstance().createWorkoutDao();
    }

    public WorkoutBean getWorkoutByAthleteName(AthleteBean athlete) {
        if (athlete == null) return null;

        Workout workout = dao.findBy(athlete.getName(), athlete.getSurname());
        if (workout == null) return null;

        WorkoutBean bean = new WorkoutBean();
        bean.setWorkoutId(workout.getWorkoutId());
        bean.setAthleteId(workout.getAthleteId());
        bean.setRepeteWeeks(workout.getRepeteWeeks());

        List<WorkoutSessionBean> sessionBeans = new ArrayList<>();

        SessionExerciseDbmsDao exDao =
                SessionExerciseDbmsDao.getInstance();

        if (workout.getSessions() != null) {
            for (WorkoutSession s : workout.getSessions()) {

                WorkoutSessionBean sb = new WorkoutSessionBean();
                sb.setDayOfWeek(s.getDayOfWeek());

                List<SessionExercise> exercises = s.getExercises();

                if (exercises == null || exercises.isEmpty()) {
                    exercises = exDao.findAllBySession(s.getSessionId());
                }

                List<SessionExerciseBean> exBeans = new ArrayList<>();
                if (exercises != null) {
                    for (SessionExercise ex : exercises) {
                        SessionExerciseBean seb =
                                new SessionExerciseBean();

                        seb.setExerciseName(ex.getExerciseName());
                        seb.setSets(ex.getSets());
                        seb.setReps(ex.getReps());
                        seb.setRestTime(ex.getRestTime());
                        seb.setType(ex.getType());   
                        seb.setNotes(ex.getNotes());

                        exBeans.add(seb);
                    }
                }

                sb.setExercises(exBeans);
                sessionBeans.add(sb);
            }
        }

        bean.setSessions(sessionBeans);
        return bean;
    }
}
