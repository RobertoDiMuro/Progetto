package it.uniroma2.dicii.ezgym.dao.abstractFactory;

import java.io.InputStream;
import java.util.Properties;

import it.uniroma2.bootstrap.Mode;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;

public abstract class DaoFactory {
    
  public abstract AthleteDao createAthleteDao();

  public abstract ExerciseDao createExerciseDao();

  public abstract SessionExerciseDao createSessionExerciseDao();

  public abstract PersonalTrainerDao createPersonalTrainerDao();

  public abstract WorkoutDao createWorkoutDao();

  public abstract WorkoutSessionDao createWorkoutSessionDao();

  public abstract UserDao createUserDao();



   private static DaoFactory instance;

   public static synchronized DaoFactory init(Mode mode){
        if(instance == null){
          switch (mode) {
            case DATABASE:
                instance = new DaoDbmsFactory();
                break;
            // case DEMO:
            //     instance = new DaoDemoFactory();
            //     break;
            // // case FILESYSTEM:
            // //     instance = new DaoFilesystemFactory();
            // //     break;
            // default:
            //     instance = new DaoDemoFactory();
            //     break;
          }
        }
        return instance;
    }

    public static synchronized DaoFactory getInstance(){
         if(instance == null){
               throw new IllegalStateException("DaoFactory is not initialized, call init(Mode) method first.");
         }
         return instance;
    }
 }
