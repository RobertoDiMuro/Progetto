// package it.uniroma2.dicii.ezgym.dao.DemoDao;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;

// import it.uniroma2.dicii.ezgym.dao.InterfaceDao.WorkoutDao;
// import it.uniroma2.dicii.ezgym.domain.model.Workout;
// import it.uniroma2.dicii.ezgym.utils.DemoMemory;

// public class WorkoutDemoDao implements WorkoutDao {
    
//     private static WorkoutDemoDao instance;
//     private final Map<String, Workout> workoutTable;

//     private WorkoutDemoDao() {
//         this.workoutTable = DemoMemory.getInstance().getWorkouts();
//     }

//     public static WorkoutDemoDao getInstance(){
//         if(instance == null){
//             instance = new WorkoutDemoDao();
//         }
//         return instance;
//     }

//     @Override
//     public boolean insert(Workout workout, String requestByAthleteName){
//         if(workoutTable.containsKey(requestByAthleteName)){
//             return false;
//         }
//         workoutTable.put(requestByAthleteName, workout);
//         return true;
//     }

//     @Override
//     public Workout findBy(String requestByAthleteName){
//         for(Workout workout : workoutTable.values()){
//             if(workout.getRequestByAthleteFullName().equals(requestByAthleteName)){
//                 return workout;
//             }
//         }
//         return null;
//     }

//     @Override
//     public List<Workout> findAll(){
//         return new ArrayList<>(workoutTable.values());
//     }

//     @Override
//     public void delete(String requestByAthleteName){
//         workoutTable.remove(requestByAthleteName);
//     }
   
// }
