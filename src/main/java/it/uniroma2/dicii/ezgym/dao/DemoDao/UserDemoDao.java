package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public class UserDemoDao implements UserDao{

    private static UserDemoDao instance = null;
    protected final Map<UUID, User> demoMemory = new HashMap<>();

    static {
        Athlete athlete = new Athlete(
            "M",                        
            25,                      
            UUID.randomUUID(),
            "Roberto",
            "Di Muro",
            "roby.dimuro@gmail.com",
            PasswordUtils.hashPassword("ovxn2!Bt"),
            Role.ATHLETE,
            75.0,                      
            180.0,                     
            null,                      
            null,                 
            null             
        );

        getInstance().insert(athlete);
}

    public static UserDemoDao getInstance() {
        if (instance == null) {
            instance = new UserDemoDao();
        }
        return instance;
    }

    @Override
    public void insert(User user){
        demoMemory.put(user.getId(), user);
    }
    
    @Override
    public User findById(UUID id){
        return demoMemory.get(id);
    }

    @Override
    public User findByEmail(String email){
        for(User user : demoMemory.values()){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(UUID id, User user){
        demoMemory.put(id, user);
    }

    @Override
    public void delete(UUID id){
        demoMemory.remove(id);
    }
}
