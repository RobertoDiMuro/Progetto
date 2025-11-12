package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.MealDao;
import it.uniroma2.dicii.ezgym.domain.model.Meal;
import it.uniroma2.dicii.ezgym.domain.model.MealType;

public class MealDemoDao extends BaseMultyEntityDemoDao<Meal, MealType> implements MealDao {

    private static MealDemoDao instance;

    public static MealDemoDao getInstance() {
        if (instance == null) {
            instance = new MealDemoDao();
        }
        return instance;
    }
    
}
