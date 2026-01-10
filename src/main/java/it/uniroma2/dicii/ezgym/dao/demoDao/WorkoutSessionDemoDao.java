package it.uniroma2.dicii.ezgym.dao.demoDao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class WorkoutSessionDemoDao implements WorkoutSessionDao {
    
    private static WorkoutSessionDemoDao instance;
    private final Map<Integer, WorkoutSession> sessionTable;

    private WorkoutSessionDemoDao() {
        this.sessionTable = DemoMemory.getInstance().getSessions();
    }

    public static WorkoutSessionDemoDao getInstance() {
        if (instance == null) {
            instance = new WorkoutSessionDemoDao();
        }
        return instance;
    }
    
    private int nextSessionId() {
        int max = 0;
        for (Integer id : sessionTable.keySet()) {
            if (id != null && id > max) {
                max = id;
            }
        }
        return max + 1;
    }

    private String norm(String s) {
        if (s == null) return null;
        String t = Normalizer.normalize(s, Normalizer.Form.NFD);
        t = t.replaceAll("\\p{M}", "");
        t = t.trim().toUpperCase();
        return t;
    }

    @Override
    public int insert(WorkoutSession session, int sessionId){
       if(session == null){
            throw new IllegalArgumentException("WorkoutSession è nullo");
        }
            int idToUse = (sessionId > 0) ? sessionId : nextSessionId();
            try {
                session.getClass().getMethod("setSessionId", int.class).invoke(session, idToUse);
            } catch (Exception ignored) {
        }

        sessionTable.put(idToUse, session);
        return idToUse;
    }

    @Override
    public WorkoutSession findBy(String day_of_week){
        if(day_of_week == null || day_of_week.isBlank()){
            return null;
        }

        String target = norm(day_of_week);

        for(WorkoutSession workoutSession : sessionTable.values()){
            if (workoutSession == null) continue;
            String stored = norm(workoutSession.getDayOfWeek());
            if(stored != null && stored.equals(target)){
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
    public void delete(int sessionId){
        sessionTable.remove(sessionId);
     }

    
}
