package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class ExerciseDemoDao implements ExerciseDao{

    private static ExerciseDemoDao instance;
    private final Map<String, Exercise> exerciseTable;

    private ExerciseDemoDao() {
        this.exerciseTable = DemoMemory.getInstance().getExercises();
    }

    public static ExerciseDemoDao getInstance(){
        if(instance == null){
            instance = new ExerciseDemoDao();
        }
        return instance;
    }
    
    @Override
    public Exercise findByFocus(String focus){
        return null;
    }

    @Override
    public void insert(Exercise exercise, String focus){
        // exerciseTable.put(exercise);
    }

    @Override
    public Exercise findBy(String name){
        // for(Exercise exercise : exerciseTable.values()){
        //     if(exercise.getName().equals(name)){
        //         return exercise;
        //     }
        // }
        return null;
    }

    @Override 
    public List<Exercise> findAll(){
        // return new ArrayList<>(exerciseTable.values());
        return null;
    }

    @Override
    public void delete(String name){
        exerciseTable.remove(name);
    }
}
