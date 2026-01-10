package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.User;

public interface UserDao {
    
    void insert(User user, UUID id);
    User findById(UUID id);
    User findByEmail(String email);
    int countAthletes();
    List<User> findAll();
    void delete(UUID id);
}
