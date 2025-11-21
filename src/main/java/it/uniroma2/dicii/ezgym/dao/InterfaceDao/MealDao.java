package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Meal;
import it.uniroma2.dicii.ezgym.domain.model.MealType;


public interface MealDao {
    
    boolean insert(Meal meal, MealType type);
    Meal findBy(MealType type);
    List<Meal> findAll();
    void update(MealType type, Meal meal);
    void delete(MealType type);
}
