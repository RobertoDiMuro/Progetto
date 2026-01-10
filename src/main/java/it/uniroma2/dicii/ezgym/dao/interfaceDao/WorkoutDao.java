package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import it.uniroma2.dicii.ezgym.domain.model.Workout;

public interface WorkoutDao {
    
    void insert(Workout workout, int workoutId);
    Workout findBy(String athleteName, String athleteSurname);
    void delete(int workoutId); 

}
