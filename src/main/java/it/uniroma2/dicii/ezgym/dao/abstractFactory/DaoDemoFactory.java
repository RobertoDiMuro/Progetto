package it.uniroma2.dicii.ezgym.dao.abstractFactory;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.AthleteDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.ExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.PersonalTrainerDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.SessionExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.UserDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.WorkoutDemoDao;
import it.uniroma2.dicii.ezgym.dao.demoDao.WorkoutSessionDemoDao;

public class DaoDemoFactory extends DaoFactory {
    
    public AthleteDao createAthleteDao(){
        return AthleteDemoDao.getInstance();
    }
    public ExerciseDao createExerciseDao(){
        return ExerciseDemoDao.getInstance();
    }
    public SessionExerciseDao createSessionExerciseDao(){
        return SessionExerciseDemoDao.getInstance();
    }
    public PersonalTrainerDao createPersonalTrainerDao(){
        return PersonalTrainerDemoDao.getInstance();
    }
    public WorkoutDao createWorkoutDao(){
        return WorkoutDemoDao.getInstance();
    }
    public WorkoutSessionDao createWorkoutSessionDao(){
        return WorkoutSessionDemoDao.getInstance();
    }
    public UserDao createUserDao(){
        return UserDemoDao.getInstance();
    }
}
