package it.uniroma2.dicii.ezgym.dao.interfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Exercise;

public interface ExerciseDao {

    void insert(Exercise exercise, String focus);
    Exercise findBy(String name);
    Exercise findByFocus(String fcous);
    List<Exercise> findAll();
    void delete(String name);
}
