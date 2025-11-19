package it.uniroma2.dicii.ezgym.dao.abstractFactory;

import it.uniroma2.dicii.ezgym.dao.DemoDao.AthleteDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.DailyMealPlanDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.DietDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.ExerciseDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.FoodDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.MealDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.PersonalTrainerDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.UserDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.WorkoutDemoDao;
import it.uniroma2.dicii.ezgym.dao.DemoDao.WorkoutSessionDemoDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DailyMealPlanDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DietDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.FoodDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.MealDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;

public class DaoDemoFactory extends DaoFactory {
    
    public AthleteDao createAthleteDao(){
        return AthleteDemoDao.getInstance();
    }
    public DailyMealPlanDao createDailyMealPlanDao(){
        return DailyMealPlanDemoDao.getInstance();
    }
    public DietDao createDietDao(){
        return DietDemoDao.getInstance();
    }
    public ExerciseDao createExerciseDao(){
        return ExerciseDemoDao.getInstance();
    }
    public FoodDao createFoodDao(){
        return FoodDemoDao.getInstance();
    }
    
    public MealDao createMealDao(){
        return MealDemoDao.getInstance();
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
