package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Exercise;

public interface ExerciseDao {

    boolean insert(Exercise exercise, String name);
    Exercise findBy(String name);
    List<Exercise> findAll();
    void update(String name, Exercise exercise);
    void delete(String name);
}
