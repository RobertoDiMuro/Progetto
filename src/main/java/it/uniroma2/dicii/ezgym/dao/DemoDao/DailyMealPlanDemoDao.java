package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DailyMealPlanDao;
import it.uniroma2.dicii.ezgym.domain.model.DailyMealPlan;

public class DailyMealPlanDemoDao extends BaseMultyEntityDemoDao<DailyMealPlan, String> implements DailyMealPlanDao {
    
    private static DailyMealPlanDemoDao instance;

    public static synchronized DailyMealPlanDemoDao getInstance() {
        if (instance == null) {
            instance = new DailyMealPlanDemoDao();
        }
        return instance;
    }
    
}
