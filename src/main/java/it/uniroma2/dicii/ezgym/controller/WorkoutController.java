package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.dbms.SessionExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutDao;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public class WorkoutController {

    private final WorkoutDao workoutDao;
    private final SessionExerciseDbmsDao sessionExerciseDao;

    public WorkoutController() {
        this(DaoFactory.getInstance().createWorkoutDao(), SessionExerciseDbmsDao.getInstance());
    }

    WorkoutController(WorkoutDao workoutDao, SessionExerciseDbmsDao sessionExerciseDao) {
        this.workoutDao = workoutDao;
        this.sessionExerciseDao = sessionExerciseDao;
    }

    public WorkoutBean getWorkoutByAthleteName(AthleteBean athlete) {
        if (!isValidAthlete(athlete)) {
            return null;
        }

        Workout workout = workoutDao.findBy(athlete.getName(), athlete.getSurname());
        if (workout == null) {
            return null;
        }

        return toWorkoutBean(workout);
    }

    private boolean isValidAthlete(AthleteBean athlete) {
        return athlete != null
                && !isBlank(athlete.getName())
                && !isBlank(athlete.getSurname());
    }

    private WorkoutBean toWorkoutBean(Workout workout) {
        WorkoutBean bean = new WorkoutBean();
        bean.setWorkoutId(workout.getWorkoutId());
        bean.setAthleteId(workout.getAthleteId());
        bean.setRepeteWeeks(workout.getRepeteWeeks());

        List<WorkoutSession> sessions = workout.getSessions();
        bean.setSessions(toSessionBeans(sessions));

        return bean;
    }

    private List<WorkoutSessionBean> toSessionBeans(List<WorkoutSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new ArrayList<>();
        }

        List<WorkoutSessionBean> sessionBeans = new ArrayList<>();
        for (WorkoutSession session : sessions) {
            if (session != null) {
                sessionBeans.add(toSessionBean(session));
            }
        }
        return sessionBeans;
    }

    private WorkoutSessionBean toSessionBean(WorkoutSession session) {
        WorkoutSessionBean sb = new WorkoutSessionBean();
        sb.setSessionId(session.getSessionId());
        sb.setDayOfWeek(session.getDayOfWeek());
        sb.setExercises(toExerciseBeans(session));
        return sb;
    }

    private List<SessionExerciseBean> toExerciseBeans(WorkoutSession session) {
        List<SessionExercise> exercises = getExercisesOrLoad(session);
        if (exercises.isEmpty()) {
            return new ArrayList<>();
        }

        List<SessionExerciseBean> exBeans = new ArrayList<>();
        for (SessionExercise ex : exercises) {
            if (ex != null) {
                exBeans.add(toExerciseBean(ex));
            }
        }
        return exBeans;
    }

    private List<SessionExercise> getExercisesOrLoad(WorkoutSession session) {
        List<SessionExercise> exercises = session.getExercises();

        if (exercises != null && !exercises.isEmpty()) {
            return exercises;
        }

        List<SessionExercise> loaded = sessionExerciseDao.findAllBySession(session.getSessionId());
        return loaded != null ? loaded : Collections.emptyList();
    }

    private SessionExerciseBean toExerciseBean(SessionExercise ex) {
        SessionExerciseBean seb = new SessionExerciseBean();
        seb.setExerciseName(ex.getExerciseName());
        seb.setSets(ex.getSets());
        seb.setReps(ex.getReps());
        seb.setRestTime(ex.getRestTime());
        seb.setType(ex.getType());
        seb.setNotes(ex.getNotes());
        return seb;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
