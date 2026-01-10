package it.uniroma2.dicii.ezgym.dao.abstractFactory;


import it.uniroma2.dicii.ezgym.dao.dbms.AthleteDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.ExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.PersonalTrainerDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.SessionExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.UserDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.WorkoutDbmsDao;
import it.uniroma2.dicii.ezgym.dao.dbms.WorkoutSessionDbmsDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.WorkoutSessionDao;

public class DaoDbmsFactory extends DaoFactory {
    
    @Override
    public AthleteDao createAthleteDao(){
        return AthleteDbmsDao.getInstance();
    }

    @Override
    public UserDao createUserDao(){
        return UserDbmsDao.getInstance();
    }

    @Override
    public PersonalTrainerDao createPersonalTrainerDao(){
        return PersonalTrainerDbmsDao.getInstance();
    }

    @Override
    public SessionExerciseDao createSessionExerciseDao(){
        return SessionExerciseDbmsDao.getInstance();
    }

    @Override
    public ExerciseDao createExerciseDao(){
        return ExerciseDbmsDao.getInstance();
    }

    @Override
    public WorkoutSessionDao createWorkoutSessionDao(){
        return WorkoutSessionDbmsDao.getInstance();
    }

     @Override
     public WorkoutDao createWorkoutDao(){
         return WorkoutDbmsDao.getInstance();
    }
}
