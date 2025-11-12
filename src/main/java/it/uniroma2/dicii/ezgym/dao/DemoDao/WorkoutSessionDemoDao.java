package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public class WorkoutSessionDemoDao extends BaseMultyEntityDemoDao<WorkoutSession, String> implements WorkoutSessionDao {
    
    private static WorkoutSessionDemoDao instance;

    public static WorkoutSessionDemoDao getInstance() {
        if (instance == null) {
            instance = new WorkoutSessionDemoDao();
        }
        return instance;
    }
    
}
