package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DailyMealPlanDao;
import it.uniroma2.dicii.ezgym.domain.model.DailyMealPlan;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class DailyMealPlanDemoDao implements DailyMealPlanDao {
    
    private static DailyMealPlanDemoDao instance;
    private final Map<String, DailyMealPlan> dailyPlanTable = InMemoryDb.getInstance().getTable(DailyMealPlan.class);



    public static synchronized DailyMealPlanDemoDao getInstance() {
        if (instance == null) {
            instance = new DailyMealPlanDemoDao();
        }
        return instance;
    }
    
    @Override
    public boolean insert(DailyMealPlan meals, String day) {
        if(dailyPlanTable.containsKey(day)){
            return false;
        }
        dailyPlanTable.put(day, meals);
        return true;
    }

    @Override
   public DailyMealPlan findBy(String day){
        for(DailyMealPlan meals : dailyPlanTable.values()){
            if(meals.getDay().equals(day)){
                return meals;
            }
        }
        return null;
   }

   @Override
   public List<DailyMealPlan> findAll(){
        return new ArrayList<>(dailyPlanTable.values());
   }

   @Override
   public void update(String day, DailyMealPlan meals){
        dailyPlanTable.put(day, meals);
   }

   @Override
   public void delete(String day){
        dailyPlanTable.remove(day);
   }
}
