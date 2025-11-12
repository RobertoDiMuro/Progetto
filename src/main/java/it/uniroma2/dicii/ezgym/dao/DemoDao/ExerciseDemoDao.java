package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;

public class ExerciseDemoDao extends BaseMultyEntityDemoDao<Exercise, String> implements ExerciseDao{

    public static ExerciseDemoDao instance;

    public static ExerciseDemoDao getInstance(){
        if(instance == null){
            instance = new ExerciseDemoDao();
        }
        return instance;
    }
    
}
