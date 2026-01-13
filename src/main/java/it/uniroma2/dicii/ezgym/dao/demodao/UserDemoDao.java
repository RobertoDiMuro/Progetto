package it.uniroma2.dicii.ezgym.dao.demodao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class UserDemoDao implements UserDao{

    private final Map<UUID, User> userTable;

    private static UserDemoDao instance;

    public static synchronized UserDemoDao getInstance(){
        if(instance == null){
            instance = new UserDemoDao();
        }
        return instance;
    }

    public UserDemoDao() {
        this.userTable = DemoMemory.getInstance().getUsers();
    }  

    @Override
    public void insert(User user, UUID id){
        if(user != null){
            user.setId(id);
        }
        userTable.put(id, user);
    }
    
    @Override
    public User findById(UUID id){
        if(id == null){
            return null;
        }

        for(User user : userTable.values()){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email){
        if(email == null){
            return null;
        }
        
        for(User user : userTable.values()){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int countAthletes(){
        int count = 0;
        for(User user : userTable.values()){
            if(user.getRole() == Role.ATHLETE){
                count++;
            }
        }
        return count;
    }

    @Override
    public List<User> findAll(){
        return new ArrayList<>(userTable.values());
    }

    @Override
    public void delete(UUID id){
        userTable.remove(id);
    }
}
