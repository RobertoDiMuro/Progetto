package it.uniroma2.dicii.ezgym.dao.InterfaceDao;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.User;

public interface UserDao {
    
    void insert(User user);
    User findById(UUID id);
    User findByEmail(String email);
    void update(UUID id, User user);
    void delete(UUID id);
}
