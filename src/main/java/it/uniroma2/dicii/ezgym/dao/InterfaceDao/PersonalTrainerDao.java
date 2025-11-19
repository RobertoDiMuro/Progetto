package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Meal;
import it.uniroma2.dicii.ezgym.domain.model.MealType;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public interface PersonalTrainerDao {
    
    boolean insert(PersonalTrainer personalTrainer, UUID id);
    PersonalTrainer findBy(String email);
    List<PersonalTrainer> findAll();
    void update(UUID id, PersonalTrainer personalTrainer);
    boolean delete(UUID id); 
}
