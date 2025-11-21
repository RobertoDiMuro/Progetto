package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public interface WorkoutSessionDao {

    boolean insert(WorkoutSession session, String sessionName);
    WorkoutSession findBy(String sessionName);
    List<WorkoutSession> findAll();
    void update(String sessionName, WorkoutSession session);
    void delete(String sessionName); 
}
