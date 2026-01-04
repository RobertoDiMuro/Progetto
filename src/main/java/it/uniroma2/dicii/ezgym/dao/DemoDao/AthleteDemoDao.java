package it.uniroma2.dicii.ezgym.dao.DemoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class AthleteDemoDao implements AthleteDao{

    private static AthleteDemoDao instance;
    private final Map<UUID, Athlete> athleteTable;

    public AthleteDemoDao() {
        this.athleteTable = DemoMemory.getInstance().getAthletes();
    }

    public static AthleteDemoDao getInstance(){
        if(instance == null){
            instance = new AthleteDemoDao();
        }
        return instance;
    }

    @Override
    public void closeRequest(UUID id){
        //
    }

   @Override
   public void insert(Athlete athlete, UUID id){
        athleteTable.put(id, athlete);
   }

    @Override
    public Athlete findBy(String email) {
        for (Athlete athlete : athleteTable.values()) {
            if (athlete.getEmail().equals(email)) {
                return athlete;
            }
        }
        return null;
    }

   @Override
   public List<Athlete> findAll() {
        return new ArrayList<>(athleteTable.values());
    }

    @Override
    public void update(UUID id, Athlete athlete){
        athleteTable.put(id, athlete);
    }

    @Override
    public void delete(UUID id){
        athleteTable.remove(id);
    }
}
