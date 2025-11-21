package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Diet;


public interface DietDao {
    
    boolean insert(Diet diet, String requestByAthleteName);
    Diet findBy(String requestByAthleteName);
    List<Diet> findAll();
    void update(String requestByAthleteName, Diet diet);
    void delete(String requestByAthleteName); 
}
