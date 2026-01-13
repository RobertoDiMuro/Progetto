package it.uniroma2.dicii.ezgym.dao.abstractfactory;

import it.uniroma2.dicii.ezgym.dao.demodao.AthleteDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.ExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.PersonalTrainerDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.SessionExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.UserDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.WorkoutDemoDao;
import it.uniroma2.dicii.ezgym.dao.demodao.WorkoutSessionDemoDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutSessionDao;

public class DaoDemoFactory extends DaoFactory {
    
    @Override
    public AthleteDao createAthleteDao() {
        return AthleteDemoDao.getInstance();
    }

    @Override
    public ExerciseDao createExerciseDao() {
        return ExerciseDemoDao.getInstance();
    }

    @Override
    public SessionExerciseDao createSessionExerciseDao() {
        return SessionExerciseDemoDao.getInstance();
    }

    @Override
    public PersonalTrainerDao createPersonalTrainerDao() {
        return PersonalTrainerDemoDao.getInstance();
    }

    @Override
    public WorkoutDao createWorkoutDao() {
        return WorkoutDemoDao.getInstance();
    }

    @Override
    public WorkoutSessionDao createWorkoutSessionDao() {
        return WorkoutSessionDemoDao.getInstance();
    }

    @Override
    public UserDao createUserDao() {
        return UserDemoDao.getInstance();
    }
}
