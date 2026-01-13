package it.uniroma2.dicii.ezgym.dao.demodao;

import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.utils.DemoMemory;

public class WorkoutDemoDao implements WorkoutDao {
    
    private final Map<Integer, Workout> workoutTable;
    private final Map<UUID, Athlete> athleteTable;

    private static WorkoutDemoDao instance;
    
    public static synchronized WorkoutDemoDao getInstance(){
        if(instance == null){
            instance = new WorkoutDemoDao();
        }
        return instance;
    }

    public WorkoutDemoDao() {
        this.workoutTable = DemoMemory.getInstance().getWorkouts();
        this.athleteTable = DemoMemory.getInstance().getAthletes();
    }


    private int nextWorkoutId() {
        int max = 0;
        for (Integer id : workoutTable.keySet()) {
            if (id != null && id > max) {
                max = id;
            }
        }
        return max + 1;
    }

    @Override
    public void insert(Workout workout, int workoutId) {
        if (workout == null) return;

        int idToUse = (workoutId > 0) ? workoutId : nextWorkoutId();

        workout.setWorkoutId(idToUse);
        workoutTable.put(idToUse, workout);
    }

    @Override
    public Workout findBy(String athleteName, String athleteSurname) {
        if (isBlank(athleteName)) {
            return null;
        }

        UUID athleteId = findAthleteIdByNameAndSurname(athleteName, athleteSurname);
        if (athleteId == null) {
            return null;
        }

        return findLatestWorkoutByAthleteId(athleteId);
    }

    private UUID findAthleteIdByNameAndSurname(String athleteName, String athleteSurname) {
        for (Athlete a : athleteTable.values()) {
            if (a == null) {
                continue;
            }

            boolean sameName = athleteName.equals(a.getName());
            boolean surnameOk = isBlank(athleteSurname) || athleteSurname.equals(a.getSurname());

            if (sameName && surnameOk) {
                return a.getId();
            }
        }
        return null;
    }

    private Workout findLatestWorkoutByAthleteId(UUID athleteId) {
        Workout best = null;
        int bestId = -1;

        for (Workout w : workoutTable.values()) {
            if (w == null) {
                continue;
            }

            if (athleteId.equals(w.getAthleteId()) && w.getWorkoutId() > bestId) {
                bestId = w.getWorkoutId();
                best = w;
            }
        }

        return best;
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    @Override
    public void delete(int workoutId) {
        workoutTable.remove(workoutId);
    }
   
}
