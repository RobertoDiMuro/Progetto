package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;

public interface SessionExerciseDao {
    
    void insert(SessionExercise sessionExercise);
    SessionExercise findBy(int sessionId, String exerciseName);
    List<SessionExercise> findAllBySession(int sessionId);
    void delete(int sessionId, String exerciseName);
}
