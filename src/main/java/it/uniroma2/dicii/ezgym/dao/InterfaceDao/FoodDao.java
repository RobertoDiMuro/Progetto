package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Food;

public interface FoodDao {
    
    boolean insert(Food food, String name);
    Food findBy(String name);
    List<Food> findAll();
    void update(String name, Food food);
    boolean delete(String name);
}
