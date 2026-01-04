package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.ExerciseBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.dao.dbms.ExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;

public class ExerciseController {
    
    private final ExerciseDao exerciseDao;

    public ExerciseController(){
        this.exerciseDao = ExerciseDbmsDao.getInstance();
    }

    public void createExercise(ExerciseBean bean){
        if(bean == null){
            throw new IllegalArgumentException("ExerciseBean non può essere nullo");
        }

        String name = bean.getName();
        String focus = bean.getFocus();

        Exercise existing = exerciseDao.findBy(name);
        if(existing != null){
            throw new IllegalArgumentException("Esercizio con questo nome già esistente");
        }

        Exercise exercise = new Exercise(
            name,
            focus);
        
        exerciseDao.insert(exercise, focus);
    }
}
