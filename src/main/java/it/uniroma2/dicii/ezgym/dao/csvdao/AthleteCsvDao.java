package it.uniroma2.dicii.ezgym.dao.csvdao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class AthleteCsvDao extends AbstractCsvDao implements AthleteDao{
    
    private static final String separator = ";";

    private static AthleteCsvDao instance;

    private AthleteCsvDao(){
        super(CsvPaths.Athletes);
    }

    public static synchronized AthleteCsvDao getInstance(){
        if(instance == null){
            instance = new AthleteCsvDao();
        }
        return instance;
    }

    @Override
    public void closeRequest(UUID id){
        //
    }

   @Override
   public void insert(Athlete athlete, UUID id){
        // String line = toCsv(athlete);
        // if(writeLine(line)){
        //     return true;
        // }
        // return false;
   }

   @Override
   public Athlete findBy(String email){
        return findAll().stream().filter(athlete -> athlete.getEmail().equals(email)).findFirst().orElse(null);
   }

   @Override
   public List<Athlete> findAll(){
        List<String> lines = readAllLines();
        List<Athlete> athletes = new ArrayList<>();
        for(String line : lines){
            athletes.add(fromCsv(line));
        }
        return athletes;
   }

   @Override
   public void update(UUID id, Athlete athlete){
        List<Athlete> all = findAll();
        boolean updated = false;

        List<String> lines = new ArrayList<>();
        for(Athlete a : all){
            if(a.getId().equals(id)){
                lines.add(toCsv(athlete));
                updated = true;
            }else{
                lines.add(toCsv(a));
            }

        }
        if(updated){
            overwriteAllLines(lines);
        }
   }

   @Override
   public void delete(UUID id){
        List<Athlete> all = findAll();
        boolean removed = all.removeIf(athlete -> athlete.getId().equals(id));

        if(!removed){
            return;
        }

        List<String> lines = new ArrayList<>();
        for(Athlete a : all){
            lines.add(toCsv(a));
        }

        overwriteAllLines(lines);
   }

   private String toCsv(Athlete athlete){
        return athlete.getId().toString() + separator + athlete.getName() + separator + athlete.getSurname() + separator +
               athlete.getEmail() + separator + athlete.getPassword() + separator + athlete.getRole() + separator +
               athlete.getGender() + separator + athlete.getAge() + separator + athlete.getHeight() + separator +
               athlete.getWeight() + separator + athlete.getTarget() + separator + athlete.getActivityLevel() + separator +
               athlete.getWorkoutDay();
   }

   private Athlete fromCsv(String line){
        String token[] = line.split(separator);
        if(token.length != 13){
            throw new PersistenceException("Riga utente malformata: " + line);
        }

        UUID id = UUID.fromString(token[0]);
        String name = token[1];
        String surname = token[2];
        String email = token[3];
        String password = token[4];
        Role role = Role.valueOf(token[5]);
        String gender = token[6];
        int age = Integer.parseInt(token[7]);
        double height = Double.parseDouble(token[8]);
        double weight = Double.parseDouble(token[9]);
        Target target = Target.valueOf(token[10]);
        ActivityLevel activityLevel = ActivityLevel.valueOf(token[11]);
        WorkoutDay workoutDay = WorkoutDay.valueOf(token[12]);

        Athlete athlete = new Athlete();
        athlete.setId(id);
        athlete.setName(name);
        athlete.setSurname(surname);
        athlete.setEmail(email);
        athlete.setPassword(password);
        athlete.setRole(role);
        athlete.setGender(gender);
        athlete.setAge(age);
        athlete.setHeight(height);
        athlete.setWeight(weight);
        athlete.setTarget(target);
        athlete.setActivityLevel(activityLevel);
        athlete.setWorkoutDay(workoutDay);
        

        return athlete;
   }
}
