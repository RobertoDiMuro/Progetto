package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class WorkoutRequestController {

    private final AthleteDao athleteDao;

    public WorkoutRequestController(){
        this.athleteDao = DaoFactory.getInstance().createAthleteDao();
    }

    public void setCurrAthlete(AthleteBean bean){
        if(bean == null){
            throw new IllegalArgumentException("AthleteBean non può essere nullo");
        }

        String email = bean.getEmail();
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email non valida.");
        }

        if(bean.getTarget() == null){
            throw new IllegalArgumentException("Target non valido.");
        }
        if(bean.getWorkoutDay() == null){
            throw new IllegalArgumentException("Giorni di allenamento non validi.");
        }
        if(bean.getActivityLevel() == null){
            throw new IllegalArgumentException("Activity level non valido.");
        }

        Athlete athlete;
        try{
            athlete = athleteDao.findBy(email);
        }catch(Exception _){
            throw new PersistenceException("Errore durante il recupero dell'atleta.");
        }

        if(athlete == null){
            throw new PersistenceException("Atleta non trovato.");
        }

        try{
            athlete.setAge(bean.getAge());
            athlete.setWeight(bean.getWeight());
            athlete.setHeight(bean.getHeight());
            athlete.setGender(bean.getGender());
            athlete.setIsWorkoutRequested(true);

            athlete.setActivityLevel(bean.getActivityLevel());
            athlete.setTarget(bean.getTarget());
            athlete.setWorkoutDay(bean.getWorkoutDay());

            athleteDao.update(athlete.getId(), athlete);
        }catch(IllegalArgumentException e){
            throw e;
        }catch(Exception _){
            throw new PersistenceException("Errore durante il salvataggio della richiesta di scheda");
        }
    }

}
