package it.uniroma2.dicii.ezgym.dao.dbms;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.InterfaceDao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class AthleteDbmsDao implements AthleteDao{
    
    private static AthleteDbmsDao instance;

    public static synchronized AthleteDbmsDao getInstance(){
        if(instance == null){
            instance = new AthleteDbmsDao();
        }
        return instance;
    }

    private static final String CALL_INSERT_ATHLETE = "{CALL insert_athlete(?,?,?,?,?,?,?,?,?)}";
    private static final String CALL_FIND_ATHLETE_BY_EMAIL = "{CALL find_athlete_by_email(?)}";
    private static final String CALL_FIND_ALL_ATHLETES = "{CALL find_all_athletes()}";
    private static final String CALL_UPDATE_ATHLETE = "{CALL update_athlete(?,?,?,?,?,?,?,?,?)}";
    private static final String CALL_CLOSE_REQUEST = "{CALL close_workout_request(?)}";
    private static final String CALL_DELETE_ATHLETE = "{CALL delete_athlete(?)}";

    @Override
    public void insert(Athlete athlete, UUID id){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            executeInsert(connection, athlete);
        }catch(Exception e){
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection connection, Athlete athlete) throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_INSERT_ATHLETE)){
            cs.setString(1, athlete.getId().toString());
            cs.setString(2, athlete.getGender());
            cs.setInt(3, athlete.getAge());
            cs.setDouble(4, athlete.getWeight());
            cs.setDouble(5, athlete.getHeight());
            cs.setBoolean(9, athlete.getIsWorkoutRequested());
            
            if(athlete.getTarget() != null){
                cs.setString(6, athlete.getTarget().name());
            }else {
            cs.setNull(6, Types.VARCHAR);
            }
            if(athlete.getActivityLevel() != null){
                cs.setString(7, athlete.getActivityLevel().name());
            }else {
            cs.setNull(7, Types.VARCHAR);
            }
            if(athlete.getWorkoutDay() != null){
                cs.setString(8, athlete.getWorkoutDay().name());
            }else {
            cs.setNull(8, Types.VARCHAR);
            }
            cs.execute();
        }
    }

    @Override
    public Athlete findBy(String email){
       try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFind(connection, email);
        }catch(SQLException e){
            throw new PersistenceException(e);
       }
    }

    private Athlete executeFind(Connection connection, String email) throws SQLException{
       
        try(CallableStatement cs = connection.prepareCall(CALL_FIND_ATHLETE_BY_EMAIL)){
            cs.setString(1, email);
            try(ResultSet rs = cs.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                return mapAthlete(rs);
            }
        }
    }

    private Athlete mapAthlete(ResultSet rs) throws SQLException{

        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String password = rs.getString("password");

        UUID id_athlete = UUID.fromString(rs.getString("id_athlete"));
        String gender = rs.getString("gender");
        int age = rs.getInt("age");
        double weight = rs.getDouble("weight");
        double height = rs.getDouble("height");
        boolean isWorkoutRequested = rs.getBoolean("is_workout_requested");

        Target target = null;
        String targetStr = rs.getString("target");
        if (targetStr != null && !targetStr.isBlank()) {
            try {
                target = Target.valueOf(targetStr);
            } catch (IllegalArgumentException e) {
                throw new PersistenceException(
                    "Obiettivo cercato non valido: " + targetStr, e
                );
            }
        }

        ActivityLevel activityLevel = null;
        String activityLevelStr = rs.getString("activity_level");
        if (activityLevelStr != null && !activityLevelStr.isBlank()) {
            try {
                activityLevel = ActivityLevel.valueOf(activityLevelStr);
            } catch (IllegalArgumentException e) {
                throw new PersistenceException(
                    "Livello di attività cercato non valido: " + activityLevelStr, e
                );
            }
        }

        WorkoutDay workoutDay = null;
        String workoutDayStr = rs.getString("workout_day");
        if (workoutDayStr != null && !workoutDayStr.isBlank()) {
            try {
                workoutDay = WorkoutDay.valueOf(workoutDayStr);
            } catch (IllegalArgumentException e) {
                throw new PersistenceException(
                    "Giorni di allenamento cercati non validi: " + workoutDayStr, e
                );
            }
        }

        Athlete athlete = new Athlete();

        athlete.setName(name);
        athlete.setSurname(surname);
        athlete.setEmail(email);
        athlete.setPassword(password);
        athlete.setId(id_athlete);
        athlete.setGender(gender);
        athlete.setAge(age);
        athlete.setWeight(weight);
        athlete.setHeight(height);
        athlete.setTarget(target);
        athlete.setActivityLevel(activityLevel);
        athlete.setWorkoutDay(workoutDay);
        athlete.setIsWorkoutRequested(isWorkoutRequested);

        return athlete;
    }

    @Override
    public List<Athlete> findAll(){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindAll(connection);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private List<Athlete> executeFindAll(Connection connection) throws SQLException {

        List<Athlete> athletes = new ArrayList<>();

        try(CallableStatement cs = connection.prepareCall(CALL_FIND_ALL_ATHLETES);
            ResultSet rs = cs.executeQuery()){

            while(rs.next()){
                athletes.add(mapAthlete(rs));
            }
            
            return athletes;

        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void update(UUID id, Athlete athlete){
        try(Connection connection = DbmsConnector.getInstance().getConnection()){
            CallableStatement cs = connection.prepareCall(CALL_UPDATE_ATHLETE);

            cs.setString(1, id.toString());
            cs.setString(2, athlete.getGender());
            cs.setInt(3, athlete.getAge());
            cs.setDouble(4, athlete.getWeight());
            cs.setDouble(5, athlete.getHeight());
            cs.setString(6, athlete.getTarget().name());
            cs.setString(7, athlete.getActivityLevel().name());
            cs.setString(8, athlete.getWorkoutDay().name());
            cs.setBoolean(9, athlete.getIsWorkoutRequested());

            cs.executeUpdate();
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void closeRequest(UUID id) {
    try (Connection conn = DbmsConnector.getInstance().getConnection();
         CallableStatement cs = conn.prepareCall(CALL_CLOSE_REQUEST)) {

        cs.setString(1, id.toString());
        cs.executeUpdate();

    } catch (SQLException e) {
        throw new PersistenceException("Errore durante la chiusura della richiesta", e);
    }
}

    @Override
    public void delete(UUID id) {
        try (Connection conn = DbmsConnector.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall(CALL_DELETE_ATHLETE)) {

            cs.setString(1, id.toString());
            cs.execute();

        } catch (SQLException e) {
            throw new PersistenceException("Errore durante l'eliminazione dell'atleta", e);
        }
    }

}
