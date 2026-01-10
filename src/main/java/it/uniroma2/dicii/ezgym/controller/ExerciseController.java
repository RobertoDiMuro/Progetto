package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.ExerciseBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class ExerciseController {
    
    private final ExerciseDao exerciseDao;

    public ExerciseController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.exerciseDao = factory.createExerciseDao();
    }

    public void createExercise(ExerciseBean bean){
        if(bean == null){
            throw new IllegalArgumentException("ExerciseBean non può essere nullo");
        }

        String name = bean.getName();
        String focus = bean.getFocus();

        if (name == null || focus == null || name.isBlank() || focus.isBlank()) {
            throw new IllegalArgumentException("Nome e focus sono obbligatori");
        }

        Exercise existing = exerciseDao.findBy(name);
        if(existing != null){
            throw new IllegalArgumentException("Esercizio con questo nome già esistente");
        }

        Exercise exercise = new Exercise(
            name,
            focus);
        
        exerciseDao.insert(exercise, focus);
    }

    public List<ExerciseBean> getAllExercises() throws PersistenceException {
        List<Exercise> all = exerciseDao.findAll();

        List<ExerciseBean> beans = new ArrayList<>();
        for (Exercise ex : all) {
            beans.add(toBean(ex));
        }
        return beans;
    }

    private ExerciseBean toBean(Exercise ex) {
        ExerciseBean b = new ExerciseBean();
        b.setName(ex.getName());
        b.setFocus(ex.getFocus());
        return b;
    }
}
