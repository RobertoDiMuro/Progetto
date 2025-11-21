package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.Athlete;

public interface AthleteDao {
    
    boolean insert(Athlete athlete, UUID id);
    Athlete findBy(String email);
    List<Athlete> findAll();
    void update(UUID id, Athlete athlete);
    void delete(UUID id); 
}
