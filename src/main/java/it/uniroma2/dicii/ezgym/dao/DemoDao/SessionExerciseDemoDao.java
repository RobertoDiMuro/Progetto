package it.uniroma2.dicii.ezgym.dao.DemoDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class SessionExerciseDemoDao implements SessionExerciseDao {

     private static SessionExerciseDemoDao instance;
    private final Map<Integer, SessionExercise> sessionExercises;

    private SessionExerciseDemoDao() {
        this.sessionExercises = DemoMemory.getInstance().getSessionExercise();
    }

    public static SessionExerciseDemoDao getInstance(){
        if(instance == null){
            instance = new SessionExerciseDemoDao();
        }
        return instance;
    }

    private Integer key(int sessionId, String exerciseName) {
        return Objects.hash(sessionId, exerciseName);
    }
    
    @Override
    public void insert(SessionExercise sessionExercise, int sessionId) {
        if(sessionExercise == null){
            throw new IllegalArgumentException("SessionExercise è nullo");
        }
        sessionExercises.put(key(sessionId, sessionExercise.getExerciseName()), sessionExercise);
    }

    @Override
    public SessionExercise findBy(int sessionId, String exerciseName){
        if (exerciseName == null || exerciseName.isBlank()) {
            return null;
        }

        return sessionExercises.get(key(sessionId, exerciseName));
    }

    @Override
    public List<SessionExercise> findAllBySession(int sessionId) {
        List<SessionExercise> res = new ArrayList<>();
        for (SessionExercise se : sessionExercises.values()) {
            if (se != null && se.getSessionId() == sessionId) {
                res.add(se);
            }
        }
        return res;
    }

    @Override
    public void delete(int sessionId, String exerciseName) {
        sessionExercises.remove(key(sessionId, exerciseName));
    }

}