package it.uniroma2.dicii.ezgym.dao.InterfaceDao;


import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Workout;

public interface WorkoutDao {
    
    boolean insert(Workout workout, String email);
    Workout findBy(String email);
    List<Workout> findAll();
    void update(String email, Workout workout);
    boolean delete(String email); 

}
