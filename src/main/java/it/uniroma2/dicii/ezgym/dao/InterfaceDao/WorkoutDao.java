package it.uniroma2.dicii.ezgym.dao.InterfaceDao;


import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Workout;

public interface WorkoutDao {
    
    boolean insert(Workout workout, String requestByAthleteName);
    Workout findBy(String requestByAthleteName);
    List<Workout> findAll();
    void update(String requestByAthleteName, Workout workout);
    void delete(String requestByAthleteName); 

}
