package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.FoodDao;
import it.uniroma2.dicii.ezgym.domain.model.Food;

public class FoodDemoDao extends BaseMultyEntityDemoDao<Food, String> implements FoodDao {

    private static FoodDemoDao instance;

    public static FoodDemoDao getInstance(){
        if(instance == null){
            instance = new FoodDemoDao();
        }
        return instance;
    }
    
}
