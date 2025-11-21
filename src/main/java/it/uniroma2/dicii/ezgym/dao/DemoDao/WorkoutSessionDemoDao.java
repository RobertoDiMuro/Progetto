package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class WorkoutSessionDemoDao implements WorkoutSessionDao {
    
    private static WorkoutSessionDemoDao instance;
    private final Map<String, WorkoutSession> sessionTable = InMemoryDb.getInstance().getTable(WorkoutSession.class);

    public static WorkoutSessionDemoDao getInstance() {
        if (instance == null) {
            instance = new WorkoutSessionDemoDao();
        }
        return instance;
    }
    
    @Override
    public boolean insert(WorkoutSession session, String sessionName){
        if(sessionTable.containsKey(sessionName)){
            return false;
        }
        sessionTable.put(sessionName, session);
        return true;
    }

    @Override
    public WorkoutSession findBy(String sessionName){
        for(WorkoutSession workoutSession : sessionTable.values()){
            if(workoutSession.getSessionName().equals(sessionName)){
                return workoutSession;
            }
        }
        return null;
    }

    @Override
    public List<WorkoutSession> findAll(){
        return new ArrayList<>(sessionTable.values());
    }

    @Override
    public void update(String sessionName, WorkoutSession workoutSession){
        sessionTable.put(sessionName, workoutSession);
    }

    @Override
    public void delete(String sessionName){
        sessionTable.remove(sessionName);
    }
}
