package it.uniroma2.dicii.ezgym.controller;


import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.dao.dbms.SessionExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class AddExerciseToSessionController {
    
    private final SessionExerciseDao dao;
    
    public AddExerciseToSessionController(){
        this.dao = SessionExerciseDbmsDao.getInstance();
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
            
            dao.insert(sessionExercise);
        }catch(Exception e){
            e.printStackTrace();
            throw new PersistenceException("Errore durante l'inserimento dell'esercizio nella sessione");
        }
    }
}
