package it.uniroma2.dicii.ezgym.dao.abstractFactory;

import java.io.InputStream;
import java.util.Properties;

import it.uniroma2.bootstrap.Mode;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DailyMealPlanDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DietDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.FoodDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.MealDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public abstract class DaoFactory {
    
   public abstract AthleteDao createAthleteDao();

   public abstract DietDao createDietDao();

   public abstract ExerciseDao createExerciseDao();

   public abstract FoodDao createFoodDao();

   public abstract MealDao createMealDao();

   public abstract PersonalTrainerDao createPersonalTrainerDao();

   public abstract WorkoutDao createWorkoutDao();

   public abstract WorkoutSessionDao createWorkoutSessionDao();

   private static DaoFactory instance;

//    public static synchronized DaoFactory getInstance(Mode mode){
//         if(instance == null){
//           switch (mode) {
//             case DATABASE:
//                 instance = new DaoDbmsFactory();
//                 break;
//             case DEMO:
//                 instance = new DaoDemoFactory();
//                 break;
//             case FILESYSTEM:
//                 instance = new DaoFilesystemFactory();
//                 break;
//             default:
//                 instance = new DaoDemoFactory();
//                 break;
//           }
//         }
//         return instance;
//     }
 }
