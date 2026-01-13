package it.uniroma2.dicii.ezgym.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
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
        


}

}
