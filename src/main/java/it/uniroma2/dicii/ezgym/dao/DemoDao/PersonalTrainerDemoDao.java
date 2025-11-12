package it.uniroma2.dicii.ezgym.dao.DemoDao;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public class PersonalTrainerDemoDao extends BaseSingleEntityDemoDao<PersonalTrainer> implements PersonalTrainerDao {

    public static PersonalTrainerDemoDao instance;

    public static PersonalTrainerDemoDao getInstance(){
        if(instance == null){
            instance = new PersonalTrainerDemoDao();
        }
        return instance;
    }

    
}
