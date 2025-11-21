package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.MealDao;
import it.uniroma2.dicii.ezgym.domain.model.Meal;
import it.uniroma2.dicii.ezgym.domain.model.MealType;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class MealDemoDao implements MealDao {

    private static MealDemoDao instance;
    private final Map<MealType, Meal> mealTable = InMemoryDb.getInstance().getTable(Meal.class);

    public static MealDemoDao getInstance() {
        if (instance == null) {
            instance = new MealDemoDao();
        }
        return instance;
    }

    @Override
    public boolean insert(Meal meal, MealType type){
        if(mealTable.containsKey(type)){
            return false;
        }
        mealTable.put(type, meal);
        return true;
    }

    @Override
    public Meal findBy(MealType type){
        for(Meal meal : mealTable.values()){
            if(meal.getType().equals(type)){
                return meal;
            }
        }
        return null;
    }
    
    @Override
    public List<Meal> findAll(){
        return new ArrayList<>(mealTable.values());
    }

    @Override
    public void update(MealType type, Meal meal){
        mealTable.put(type, meal);
    }

    @Override
    public void delete(MealType type){
        mealTable.remove(type);
    }
}
