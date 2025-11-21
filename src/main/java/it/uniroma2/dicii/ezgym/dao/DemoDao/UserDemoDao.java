package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;
import it.uniroma2.dicii.ezgym.utils.PasswordUtils;

public class UserDemoDao implements UserDao{

    private static UserDemoDao instance = null;
    private final Map<UUID, User> userTable = InMemoryDb.getInstance().getTable(User.class);

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

        getInstance().insert(athlete, athlete.getId());
}

    public static UserDemoDao getInstance() {
        if (instance == null) {
            instance = new UserDemoDao();
        }
        return instance;
    }

    @Override
    public boolean insert(User user, UUID id){
        if(userTable.containsKey(id)){
            return false;
        }
        userTable.put(id, user);
        return true;
    }
    
    @Override
    public User findById(UUID id){
        for(User user : userTable.values()){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email){
        for(User user : userTable.values()){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll(){
        return new ArrayList<>(userTable.values());
    }

    @Override
    public void update(UUID id, User user){
        userTable.put(id, user);
    }

    @Override
    public void delete(UUID id){
        userTable.remove(id);
    }
}
