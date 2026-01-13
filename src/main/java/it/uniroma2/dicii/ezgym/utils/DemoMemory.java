package it.uniroma2.dicii.ezgym.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;

public class DemoMemory {
    
    private static DemoMemory instance;
    
    private final Map<UUID, Athlete> athletes;
    private final Map<String, Exercise> exercises;
    private final Map<Integer, SessionExercise> sessionExercises;
    private final Map<UUID, PersonalTrainer> trainers;
    private final Map<UUID, User> users;
    private final Map<Integer, Workout> workouts;
    private final Map<Integer, WorkoutSession> sessions;

    private DemoMemory(){
        this.athletes = new HashMap<>();
        this.exercises = new HashMap<>();
        this.sessionExercises = new HashMap<>();
        this.trainers = new HashMap<>();
        this.users = new HashMap<>();
        this.workouts = new HashMap<>();
        this.sessions = new HashMap<>();

        seedDemoDataIfEmpty();
    }

    public static synchronized DemoMemory getInstance(){
        if(instance == null){
            instance = new DemoMemory();
        }
        return instance;
    }

    public Map<UUID, Athlete> getAthletes(){
        return athletes;
    }
    public Map<String, Exercise> getExercises(){
        return exercises;
    }
    public Map<Integer, SessionExercise> getSessionExercise(){
        return sessionExercises;
    }
    public  Map<UUID, PersonalTrainer> getTrainers(){
        return trainers;
    }
    public Map<UUID, User> getUsers(){
        return users;
    }
    public Map<Integer, Workout> getWorkouts(){
        return workouts;
    }
    public Map<Integer, WorkoutSession> getSessions(){
        return sessions;
    }

    private void seedDemoDataIfEmpty(){
    if(!athletes.isEmpty()){
        return;
    }

    UUID athleteId = UUID.randomUUID();

    Athlete a = new Athlete();
    a.setId(athleteId);
    a.setName("Franco");
    a.setSurname("Verdi");
    a.setEmail("franco.verdi@gmail.com");
    a.setPassword(PasswordUtils.hashPassword("Franco123!"));
    a.setRole(it.uniroma2.dicii.ezgym.domain.model.Role.ATHLETE);
    a.setGender("Maschio");
    a.setAge(25);
    a.setWeight(75.0);
    a.setHeight(180.0);
    a.setTarget(Target.MASSA_MUSCOLARE);
    a.setActivityLevel(ActivityLevel.LEGGERMENTE_ATTIVO);
    a.setWorkoutDay(WorkoutDay.TRE_VOLTE);
    a.setIsWorkoutRequested(false);

    
    athletes.put(athleteId, a);

    Exercise e = new Exercise();
    e.setName("Panca piana");
    e.setFocus("Petto");

    List<SessionExercise> exercises = new ArrayList<>();

    WorkoutSession ws = new WorkoutSession();
    ws.setSessionId(0);
    ws.setDayOfWeek("Primo giorno");
    ws.setExercises(exercises);

    SessionExercise se = new SessionExercise();
    se.setSessionId(0);
    se.setExerciseName(e.getName());
    se.setSets(3);
    se.setReps(12);
    se.setRestTime(120);
    se.setType(ExerciseType.FORZA);
    se.setNotes("");

    exercises.add(se);

    List<WorkoutSession> sessions = new ArrayList<>();

    sessions.add(ws);

    Workout w = new Workout();
    w.setWorkoutId(0);
    w.setAthleteId(athleteId);
    w.setRepeteWeeks(4);
    w.setSessions(sessions);

        


}

}
