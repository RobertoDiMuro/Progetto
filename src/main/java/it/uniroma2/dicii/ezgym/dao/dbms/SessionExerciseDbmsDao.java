package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.dao.interfaceDao.SessionExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class SessionExerciseDbmsDao implements SessionExerciseDao {

    private static SessionExerciseDbmsDao instance;

    public static synchronized SessionExerciseDbmsDao getInstance(){
        if(instance == null){
            instance = new SessionExerciseDbmsDao();
        }
        return instance;
    }

    private static final String CALL_INSERT_EXERCISE_IN_SESSION = "{CALL insert_exercise_in_session(?,?,?,?,?,?,?)}";
    private static final String CALL_FIND_SESSION_EXERCISE = "{CALL find_session_exercise(?,?)}";
    private static final String CALL_FIND_EXERCISES_IN_SESSION = "{CALL find_exercises_in_session(?)}";
    private static final String CALL_DELETE_EXERCISE_FROM_SESSION = "{CALL delete_exercise_from_session(?,?)}";

    @Override
    public void insert(SessionExercise sessionExercise, int sessionId){
        try{
            Connection conn = DbmsConnector.getInstance().getConnection();
            executeInsert(conn, sessionExercise);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection conn, SessionExercise sessionExercise) throws SQLException{
        try(CallableStatement cs = conn.prepareCall(CALL_INSERT_EXERCISE_IN_SESSION)){
            cs.setString(1, sessionExercise.getExerciseName());
            cs.setInt(2, sessionExercise.getSessionId());
            cs.setInt(3, sessionExercise.getSets());
            cs.setInt(4, sessionExercise.getReps());
            cs.setDouble(5, sessionExercise.getRestTime());
            cs.setString(6, sessionExercise.getType().name());
            cs.setString(7, sessionExercise.getNotes());

            cs.execute();
        }
    }

    @Override
    public SessionExercise findBy(int sessionId, String exerciseName){
        try{
            Connection conn = DbmsConnector.getInstance().getConnection();
            return executeFindBy(conn, sessionId, exerciseName);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private SessionExercise executeFindBy(Connection conn, int sessionId, String exerciseName) throws SQLException{
        try(CallableStatement cs = conn.prepareCall(CALL_FIND_SESSION_EXERCISE)){
            cs.setInt(1, sessionId);
            cs.setString(2, exerciseName);
            try(ResultSet rs = cs.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                return mapCurrentRow(rs);
            }
        }
    }

    @Override
    public List<SessionExercise> findAllBySession(int sessionId){
        try{
            Connection conn = DbmsConnector.getInstance().getConnection();
            return executeFindAllBySession(conn, sessionId);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private List<SessionExercise> executeFindAllBySession(Connection conn, int sessionId) throws SQLException{
        List<SessionExercise> sessionExercises = new ArrayList<>();

        try(CallableStatement cs = conn.prepareCall(CALL_FIND_EXERCISES_IN_SESSION)){
            cs.setInt(1, sessionId);
            try(ResultSet rs = cs.executeQuery()){
                while(rs.next()){
                    sessionExercises.add(mapCurrentRow(rs)); 
                }
            }
        }

        return sessionExercises;
    }

    @Override
    public void delete(int sessionId, String exerciseName){
        try{
            Connection conn = DbmsConnector.getInstance().getConnection();
            try(CallableStatement cs = conn.prepareCall(CALL_DELETE_EXERCISE_FROM_SESSION)){
                cs.setInt(1, sessionId);
                cs.setString(2, exerciseName);
                cs.execute();
            }
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private SessionExercise mapCurrentRow(ResultSet rs) throws SQLException{
        String exerciseName = rs.getString("exercise_name");
        int sessionId = rs.getInt("session_id");
        int sets = rs.getInt("sets");
        int reps = rs.getInt("reps");
        double restTime = rs.getDouble("rest_time");
        String notes = rs.getString("notes");

        ExerciseType type = null;
        String typeStr = rs.getString("type");
        if (typeStr != null && !typeStr.isBlank()) {
            try {
                type = ExerciseType.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                throw new PersistenceException("Tipo esercizio non valido: " + typeStr, e);
            }
        }

        return new SessionExercise(sessionId, exerciseName, sets, reps, restTime, type, notes);
    }
}
