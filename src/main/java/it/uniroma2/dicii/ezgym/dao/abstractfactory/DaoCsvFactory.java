package it.uniroma2.dicii.ezgym.dao.abstractfactory;

import it.uniroma2.dicii.ezgym.dao.csvdao.UserCsvDao;
import it.uniroma2.dicii.ezgym.dao.csvdao.AthleteCsvDao;
import it.uniroma2.dicii.ezgym.dao.csvdao.PersonalTrainerCsvDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutSessionDao;

public class DaoCsvFactory extends DaoFactory{

    @Override
    public UserDao createUserDao() {
        return UserCsvDao.getInstance();
    }

    @Override
    public PersonalTrainerDao createPersonalTrainerDao() {
        return PersonalTrainerCsvDao.getInstance();
    }

    @Override
    public AthleteDao createAthleteDao() {
        return AthleteCsvDao.getInstance();
    }
    
    @Override
    public ExerciseDao createExerciseDao(){
        return null;
    }
    
    @Override
    public SessionExerciseDao createSessionExerciseDao(){
        return null;
    }

    @Override
    public WorkoutSessionDao createWorkoutSessionDao(){
        return null;
    }
    
    @Override
    public WorkoutDao createWorkoutDao(){
        return null;
    }
    
}
