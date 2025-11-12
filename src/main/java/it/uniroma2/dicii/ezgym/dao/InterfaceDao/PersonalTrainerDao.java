package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public interface PersonalTrainerDao {
    
    boolean insert(PersonalTrainer personalTrainer);
    PersonalTrainer get();
    void update(PersonalTrainer personalTrainer);
    void delete();
}
