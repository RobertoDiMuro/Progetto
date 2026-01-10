package it.uniroma2.dicii.ezgym.dao.demoDao;

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
    public void closeRequest(UUID id) {
        Athlete athlete = athleteTable.get(id);

        if (athlete != null) {
            athlete.setIsWorkoutRequested(false);
        }
    }

   @Override
   public void insert(Athlete athlete, UUID id){
      if (athlete != null) {
            athlete.setId(id);
        }
        athleteTable.put(id, athlete);
   }

    @Override
    public Athlete findBy(String email) {
        if (email == null) {
            return null;
        }
        for (Athlete athlete : athleteTable.values()) {
            if (athlete != null && email.equals(athlete.getEmail())) {
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
    public void update(UUID id, Athlete athlete) {
        
        Athlete current = athleteTable.get(id);

        if (current == null) {
            if (athlete != null) {
                athlete.setId(id);
            }
            athleteTable.put(id, athlete);
            return;
        }

        if (athlete == null) {
            return;
        }

        current.setId(id);

        current.setGender(athlete.getGender());
        current.setAge(athlete.getAge());
        current.setWeight(athlete.getWeight());
        current.setHeight(athlete.getHeight());
        current.setTarget(athlete.getTarget());
        current.setActivityLevel(athlete.getActivityLevel());
        current.setWorkoutDay(athlete.getWorkoutDay());
        current.setIsWorkoutRequested(athlete.getIsWorkoutRequested());
    }

    @Override
    public void delete(UUID id){
        athleteTable.remove(id);
    }
}
