package it.uniroma2.dicii.ezgym.dao.abstractFactory;

import it.uniroma2.dicii.ezgym.dao.DemoDao.AthleteDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.ExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.PersonalTrainerDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.SessionExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.UserDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.WorkoutDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.WorkoutSessionDemoDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;

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
