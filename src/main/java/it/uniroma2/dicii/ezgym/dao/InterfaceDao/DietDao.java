package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;

import it.uniroma2.dicii.ezgym.domain.model.Diet;


public interface DietDao {
    
    boolean insert(Diet diet, String email);
    Diet findBy(String email);
    List<Diet> findAll();
    void update(String email, Diet diet);
    boolean delete(String email); 
}
