package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfaceDao.WorkoutDao;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import it.uniroma2.dicii.ezgym.domain.model.SessionExercise;
import it.uniroma2.dicii.ezgym.domain.model.Workout;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class WorkoutDbmsDao implements WorkoutDao{
    
    public static WorkoutDbmsDao instance;

    public static synchronized WorkoutDbmsDao getInstance(){
        if(instance == null){
            instance = new WorkoutDbmsDao();
        }
        return instance;
    }

    private static final String CALL_INSERT_WK = "{CALL insert_workout(?,?,?)}";
    private static final String CALL_LINK_SESSION = "{CALL insert_wk_plan_session(?,?)}";
    private static final String CALL_FIND_BY_ATHLETE_NAME = "{CALL find_by_athlete_name(?,?)}";
    private static final String CALL_DELETE_WORKOUT = "{CALL delete_workout(?)}";

    @Override
    public void insert(Workout workout, int workoutId) {
        try {
            Connection conn = DbmsConnector.getInstance().getConnection();

            int newWorkoutId = executeInsert(conn, workout);
            workout.setWorkoutId(newWorkoutId);

            for (WorkoutSession s : workout.getSessions()) {
                executeInsertWkPlanSession(conn, newWorkoutId, s.getSessionId());
            }

        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private int executeInsert(Connection conn, Workout workout) throws SQLException {
        try (CallableStatement cs = conn.prepareCall(CALL_INSERT_WK)) {

            cs.setString(1, workout.getAthleteId().toString());
            cs.setInt(2, workout.getRepeteWeeks());
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();
            return cs.getInt(3);
        }
    }

    private void executeInsertWkPlanSession(Connection conn, int workoutId, int sessionId) throws SQLException {
        try (CallableStatement cs = conn.prepareCall(CALL_LINK_SESSION)) {
            cs.setInt(1, workoutId);
            cs.setInt(2, sessionId);
            cs.execute();
        }
    }

    @Override
    public Workout findBy(String athleteName, String athleteSurname){
        try{
            Connection conn = DbmsConnector.getInstance().getConnection();
            return executeFindBy(conn, athleteName, athleteSurname);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private Workout executeFindBy(Connection conn, String athleteName, String athleteSurname) throws SQLException {
        try (CallableStatement cs = conn.prepareCall(CALL_FIND_BY_ATHLETE_NAME)) {

            cs.setString(1, athleteName);
            cs.setString(2, athleteSurname);

            try (ResultSet rs = cs.executeQuery()) {
                return mapWorkoutPlan(rs); 
            }
        }
    }


    private Workout mapWorkoutPlan(ResultSet rs) throws SQLException{

        if (!rs.next()) {
            return null;
        }

        int workoutId = rs.getInt("workout_id");
        UUID athleteId = UUID.fromString(rs.getString("athlete_id"));
        int repeteWeeks = rs.getInt("repete_weeks");

        List<WorkoutSession> sessions = new ArrayList<>();

        int currentSessionId = -1;
        List<SessionExercise> currentExercises = null;

        do {
            int sessionId = rs.getInt("session_id");
            String dayOfWeek = rs.getString("day_of_week");

            if (sessionId != currentSessionId) {
                currentSessionId = sessionId;
                currentExercises = new ArrayList<>();
                sessions.add(new WorkoutSession(sessionId, dayOfWeek, currentExercises));
            }

            String exerciseName = rs.getString("exercise_name");
            if (exerciseName != null && !exerciseName.isBlank()) {
                currentExercises.add(mapSessionExerciseFromRow(rs, sessionId, exerciseName));
            }

        } while (rs.next());

        return new Workout(workoutId, athleteId, repeteWeeks, sessions);
    }

    private SessionExercise mapSessionExerciseFromRow(ResultSet rs, int sessionId, String exerciseName) throws SQLException {

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

        return new SessionExercise(
            sessionId,
            exerciseName,
            sets,
            reps,
            restTime,
            type,
            notes
        );
    }

    @Override
    public void delete(int workoutId) {
        try {
            Connection conn = DbmsConnector.getInstance().getConnection();
            executeDelete(conn, workoutId);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private void executeDelete(Connection conn, int workoutId) throws SQLException {
        try (CallableStatement cs = conn.prepareCall(CALL_DELETE_WORKOUT)) {
            cs.setInt(1, workoutId);
            cs.execute();
        }
    }
}
