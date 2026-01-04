package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public interface PersonalTrainerDao {
    
    void insert(PersonalTrainer personalTrainer, UUID id);
    PersonalTrainer findBy(String email);
    void delete(UUID id); 
}
