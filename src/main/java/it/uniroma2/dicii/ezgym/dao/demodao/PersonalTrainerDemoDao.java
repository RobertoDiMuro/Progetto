package it.uniroma2.dicii.ezgym.dao.demodao;

import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class PersonalTrainerDemoDao implements PersonalTrainerDao {

    private final Map<UUID, PersonalTrainer> ptTable;
    private static PersonalTrainerDemoDao instance;

    public static synchronized PersonalTrainerDemoDao getInstance(){
        if(instance == null){
            instance = new PersonalTrainerDemoDao();
        }
        return instance;
    }

    public PersonalTrainerDemoDao() {
        this.ptTable = DemoMemory.getInstance().getTrainers();
    }


    @Override 
    public void insert(PersonalTrainer personalTrainer, UUID id){
        if(personalTrainer != null){
            personalTrainer.setId(id);
        }
        ptTable.put(id, personalTrainer);
    }

    @Override
    public PersonalTrainer findBy(String email){
        if(email == null){
            return null;
        }

        for(PersonalTrainer personalTrainer : ptTable.values()){
            if(personalTrainer.getEmail().equals(email)){
                return personalTrainer;
            }
        }
        return null;
    }

    @Override
    public void delete(UUID id){
        ptTable.remove(id);
    }
    
}
