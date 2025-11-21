package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class PersonalTrainerDemoDao implements PersonalTrainerDao {

    private static PersonalTrainerDemoDao instance;
    private final Map<UUID, PersonalTrainer> ptTable = InMemoryDb.getInstance().getTable(PersonalTrainer.class);


    public static PersonalTrainerDemoDao getInstance(){
        if(instance == null){
            instance = new PersonalTrainerDemoDao();
        }
        return instance;
    }

    @Override 
    public boolean insert(PersonalTrainer personalTrainer, UUID id){
        if(ptTable.containsKey(id)){
            return false;
        }
        ptTable.put(id, personalTrainer);
        return true;
    }

    @Override
    public PersonalTrainer findBy(String email){
        for(PersonalTrainer personalTrainer : ptTable.values()){
            if(personalTrainer.getEmail().equals(email)){
                return personalTrainer;
            }
        }
        return null;
    }

    @Override
    public List<PersonalTrainer> findAll(){
        return new ArrayList<>(ptTable.values());
    }

    @Override 
    public void update(UUID id, PersonalTrainer personalTrainer){
        ptTable.put(id, personalTrainer);
    }

    @Override
    public void delete(UUID id){
        ptTable.remove(id);
    }

    
}
