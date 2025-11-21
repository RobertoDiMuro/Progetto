package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.DietDao;
import it.uniroma2.dicii.ezgym.domain.model.Diet;
import it.uniroma2.dicii.ezgym.utils.InMemoryDb;

public class DietDemoDao implements DietDao {

    private static DietDemoDao instance;
    private final Map<String, Diet> dietTable = InMemoryDb.getInstance().getTable(Diet.class);


    private DietDemoDao() {
    }

    public static synchronized DietDemoDao getInstance() {
        if (instance == null) {
            instance = new DietDemoDao();
        }
        return instance;
    }   
    
    @Override
    public boolean insert(Diet diet, String requestByAthleteName){
        if(dietTable.containsKey(requestByAthleteName)){
            return false;
        }
        dietTable.put(requestByAthleteName, diet);
        return true;
    }

    @Override
    public Diet findBy(String requestByAthleteName){
        for(Diet diet : dietTable.values()){
            if(diet.getRequestByAthleteName().equals(requestByAthleteName)){
                return diet;
            }
        }
        return null;
    }

    @Override
    public List<Diet> findAll(){
        return new ArrayList<>(dietTable.values());
    }

    @Override
    public void update(String requestByAthleteName, Diet diet){
        dietTable.put(requestByAthleteName, diet);
    }

    @Override
    public void delete(String requestByAthleteName){
        dietTable.remove(requestByAthleteName);
    }
}
