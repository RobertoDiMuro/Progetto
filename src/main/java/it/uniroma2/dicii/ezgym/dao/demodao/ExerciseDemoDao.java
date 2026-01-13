package it.uniroma2.dicii.ezgym.dao.demodao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class ExerciseDemoDao implements ExerciseDao{

    private final Map<String, Exercise> exerciseTable;
    private static ExerciseDemoDao instance;

    public ExerciseDemoDao() {
        this.exerciseTable = DemoMemory.getInstance().getExercises();
    }

    public static synchronized ExerciseDemoDao getInstance(){
        if(instance == null){
            instance = new ExerciseDemoDao();
        }
        return instance;
    }

    @Override
    public void insert(Exercise exercise, String focus){
        if(exercise == null){
            throw new IllegalArgumentException("L'esercizio è nullo");
        }
        exerciseTable.put(focus,exercise);
    }

    @Override
    public Exercise findByFocus(String focus) {
        if (focus == null) {
            return null;
        }
        for (Exercise ex : exerciseTable.values()) {
            if (ex != null && focus.equals(ex.getFocus())) {
                return ex;
            }
        }
        return null;
    }

    @Override
    public Exercise findBy(String name) {
        if (name == null) {
            return null;
        }
        for (Exercise ex : exerciseTable.values()) {
            if (ex != null && name.equals(ex.getName())) {
                return ex;
            }
        }
        return null;
    }

    @Override 
    public List<Exercise> findAll(){
        return new ArrayList<>(exerciseTable.values());
    }

    @Override
    public void delete(String name){
        exerciseTable.remove(name);
    }
}
