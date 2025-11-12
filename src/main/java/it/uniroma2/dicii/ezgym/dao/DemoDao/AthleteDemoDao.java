package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;

public class AthleteDemoDao extends BaseMultyEntityDemoDao<Athlete, UUID> implements AthleteDao{

    private static AthleteDemoDao instance;

    public static AthleteDemoDao getInstance(){
        if(instance == null){
            instance = new AthleteDemoDao();
        }
        return instance;
    }

   
}
