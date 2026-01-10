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
    
    public AthleteDao createAthleteDao(){
        return new AthleteDemoDao();
    }
    public ExerciseDao createExerciseDao(){
        return new ExerciseDemoDao();
    }
    public SessionExerciseDao createSessionExerciseDao(){
        return new SessionExerciseDemoDao();
    }
    public PersonalTrainerDao createPersonalTrainerDao(){
        return new PersonalTrainerDemoDao();
    }
    public WorkoutDao createWorkoutDao(){
        return new WorkoutDemoDao();
    }
    public WorkoutSessionDao createWorkoutSessionDao(){
        return new WorkoutSessionDemoDao();
    }
    public UserDao createUserDao(){
        return new UserDemoDao();
    }
}
