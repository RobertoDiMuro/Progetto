package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.FoodDao;
import it.uniroma2.dicii.ezgym.domain.model.Food;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class FoodDemoDao implements FoodDao {

    private static FoodDemoDao instance;
    private final Map<String, Food> foodTable = InMemoryDb.getInstance().getTable(Food.class);


    public static FoodDemoDao getInstance(){
        if(instance == null){
            instance = new FoodDemoDao();
        }
        return instance;
    }

    @Override
    public boolean insert(Food food, String name){
        if(foodTable.containsKey(name)){
            return false;
        }
        foodTable.put(name, food);
        return true;
    }

    @Override
    public Food findBy(String name){
        for(Food food : foodTable.values()){
            if(food.getName().equals(name)){
                return food;
            }
        }
        return null;
    }

    @Override
    public List<Food> findAll(){
        return new ArrayList<>(foodTable.values());
    }

    @Override
    public void update(String name, Food food){
        foodTable.put(name, food);
    }

    @Override
    public void delete(String name){
        foodTable.remove(name);
    }
    
}
