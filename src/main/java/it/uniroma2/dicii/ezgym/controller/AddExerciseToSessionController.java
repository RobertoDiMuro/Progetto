package it.uniroma2.dicii.ezgym.controller;


import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class AddExerciseToSessionController {
    
    private final SessionExerciseDao dao;
    
    public AddExerciseToSessionController(){
        DaoFactory factory = DaoFactory.getInstance();
        this.dao = factory.createSessionExerciseDao();
    }

    public void addExerciseToSession(SessionExerciseBean bean){
        try{
            int sessionId = bean.getSessionId();
            String exerciseName = bean.getExerciseName();

            SessionExercise existing = dao.findBy(sessionId, exerciseName);
            if(existing != null){
                throw new IllegalArgumentException("Esercizio già presente nella sessione");
            }

            SessionExercise sessionExercise = new SessionExercise(
                    sessionId,
                    exerciseName,
                    bean.getSets(),
                    bean.getReps(),
                    bean.getRestTime(),
                    bean.getType(),
                    bean.getNotes());
            
            dao.insert(sessionExercise, sessionId);
        }catch(Exception e){
            throw new PersistenceException("Errore durante l'inserimento dell'esercizio nella sessione");
        }
    }
}
