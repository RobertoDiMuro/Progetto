package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.User;

public interface UserDao {
    
    boolean insert(User user, UUID id);
    User findById(UUID id);
    User findByEmail(String email);
    List<User> findAll();
    void update(UUID id, User user);
    void delete(UUID id);
}
