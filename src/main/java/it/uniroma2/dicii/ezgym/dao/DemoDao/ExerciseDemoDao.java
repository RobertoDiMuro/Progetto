package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class ExerciseDemoDao implements ExerciseDao{

    private static ExerciseDemoDao instance;
    private final Map<String, Exercise> exerciseTable = InMemoryDb.getInstance().getTable(Exercise.class);

    public static ExerciseDemoDao getInstance(){
        if(instance == null){
            instance = new ExerciseDemoDao();
        }
        return instance;
    }
    
    @Override
    public boolean insert(Exercise exercise, String name){
        if(exerciseTable.containsKey(name)){
            return false;
        }
        exerciseTable.put(name, exercise);
        return true;
    }

    @Override
    public Exercise findBy(String name){
        for(Exercise exercise : exerciseTable.values()){
            if(exercise.getName().equals(name)){
                return exercise;
            }
        }
        return null;
    }

    @Override 
    public List<Exercise> findAll(){
        return new ArrayList<>(exerciseTable.values());
    }

    @Override
    public void update(String name, Exercise exercise){
        exerciseTable.put(name, exercise);
    }

    @Override
    public void delete(String name){
        exerciseTable.remove(name);
    }
}
