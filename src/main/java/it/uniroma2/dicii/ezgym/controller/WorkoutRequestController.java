package it.uniroma2.dicii.ezgym.controller;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class WorkoutRequestController {

    private final AthleteDao athleteDao;

    public WorkoutRequestController(){
        this.athleteDao = DaoFactory.getInstance().createAthleteDao();
    }

    public void setCurrAthlete(Athlete athlete, AthleteBean bean){
        if(athlete == null || bean == null){
            throw new IllegalArgumentException("Athlete e Bean non possono essere nulli!");
        }

        try{
            athlete.setAge(bean.getAge());
            athlete.setWeight(bean.getWeight());
            athlete.setHeight(bean.getHeight());
            athlete.setGender(bean.getGender());
            athlete.setIsWorkoutRequested(bean.getIsWorkoutRequested());
            
            if(bean.getActivityLevel() != null){
                athlete.setActivityLevel(bean.getActivityLevel());
            }
            if(bean.getTarget() != null){
                athlete.setTarget(bean.getTarget());
            }
            if(bean.getWorkoutDay() != null){
                athlete.setWorkoutDay(bean.getWorkoutDay());
            }
            athleteDao.update(athlete.getId(),athlete);
        }catch(Exception e){
            e.printStackTrace();
            throw new PersistenceException("Errore durante il salvataggio della richiesta di scheda");
        }
    }
}
