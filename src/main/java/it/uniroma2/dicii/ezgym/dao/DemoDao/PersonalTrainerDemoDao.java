package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public class PersonalTrainerDemoDao extends BaseMultyEntityDemoDao<PersonalTrainer, UUID> implements PersonalTrainerDao {

    private static PersonalTrainerDemoDao instance;

    public static PersonalTrainerDemoDao getInstance(){
        if(instance == null){
            instance = new PersonalTrainerDemoDao();
        }
        return instance;
    }

    @Override
    public PersonalTrainer findBy(String email) {
        for(PersonalTrainer personalTrainer : demoMemory.values()){
            if(personalTrainer.getEmail().equals(email)){
                return personalTrainer;
            }
        }
        return null;
    }

    
}
