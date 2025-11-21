package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.DailyMealPlan;


public interface DailyMealPlanDao {
    boolean insert(DailyMealPlan meals, String day);
    DailyMealPlan findBy(String day);
    List<DailyMealPlan> findAll();
    void update(String day, DailyMealPlan meals);
    void delete(String day);
}
