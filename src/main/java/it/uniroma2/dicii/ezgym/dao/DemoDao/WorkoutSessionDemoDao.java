package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class WorkoutSessionDemoDao implements WorkoutSessionDao {
    
    private static WorkoutSessionDemoDao instance;
    private final Map<String, WorkoutSession> sessionTable;

    private WorkoutSessionDemoDao() {
        this.sessionTable = DemoMemory.getInstance().getSessions();
    }

    public static WorkoutSessionDemoDao getInstance() {
        if (instance == null) {
            instance = new WorkoutSessionDemoDao();
        }
        return instance;
    }
    
    @Override
    public int insert(WorkoutSession session){
        // if(sessionTable.containsKey(sessionName)){
             return 0;
        // }
        // sessionTable.put(sessionName, session);
        // return true;
    }

    @Override
    public WorkoutSession findBy(String day_of_week){
        // for(WorkoutSession workoutSession : sessionTable.values()){
        //     if(workoutSession.getSessionName().equals(sessionName)){
        //         return workoutSession;
        //     }
        // }
        return null;
    }

    @Override
    public List<WorkoutSession> findAll(){
        return new ArrayList<>(sessionTable.values());
    }

    @Override
    public void delete(int sessionId){
    //     sessionTable.remove(sessionId);
     }

    
}
