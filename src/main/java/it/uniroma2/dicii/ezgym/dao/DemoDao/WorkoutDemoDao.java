package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.domain.model.Workout;

public class WorkoutDemoDao extends BaseMultyEntityDemoDao<Workout, String> implements WorkoutDao {
    
    private static WorkoutDemoDao instance;

    public static WorkoutDemoDao getInstance(){
        if(instance == null){
            instance = new WorkoutDemoDao();
        }
        return instance;
    }
   
}
