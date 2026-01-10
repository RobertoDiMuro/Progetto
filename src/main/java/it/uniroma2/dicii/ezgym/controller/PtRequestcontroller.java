package it.uniroma2.dicii.ezgym.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class PtRequestcontroller {
    
    private final AthleteDao athleteDao;

    public PtRequestcontroller(){
        this.athleteDao = DaoFactory.getInstance().createAthleteDao();
    }

    public List<AthleteBean> getAthletesRequest() throws PersistenceException{
        
        try{
            List<Athlete> athletes = athleteDao.findAll();
            List<AthleteBean> result = new ArrayList<>();

            for(Athlete a : athletes){
                if(a.getIsWorkoutRequested()){
                    
                    AthleteBean bean = new AthleteBean();

                    bean.setId(a.getId());
                    bean.setName(a.getName());
                    bean.setSurname(a.getSurname());
                    bean.setEmail(a.getEmail());

                    bean.setGender(a.getGender());
                    bean.setAge(a.getAge());
                    bean.setWeight(a.getWeight());
                    bean.setHeight(a.getHeight());
                    bean.setTarget(a.getTarget());
                    bean.setActivityLevel(a.getActivityLevel());
                    bean.setWorkoutDay(a.getWorkoutDay());

                    result.add(bean);
                }

                
            }
            return result;
        }catch(Exception e){
            throw new PersistenceException("Errore nel recupero delle richieste degli atleti.", e);
        }
    }

    public void closeRequest(UUID athleteId) {
        athleteDao.closeRequest(athleteId);
    }
}
