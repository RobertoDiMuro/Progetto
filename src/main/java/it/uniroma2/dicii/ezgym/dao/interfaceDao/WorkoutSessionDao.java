package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public interface WorkoutSessionDao {

    int insert(WorkoutSession session, int sessionId);
    WorkoutSession findBy(String dayOfWeek);
    List<WorkoutSession> findAll();
    void delete(int sessionId);
}
