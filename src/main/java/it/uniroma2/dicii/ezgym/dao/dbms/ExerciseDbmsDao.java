package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class ExerciseDbmsDao implements ExerciseDao{
    
    private static ExerciseDbmsDao instance;

    public static synchronized ExerciseDbmsDao getInstance(){
        if(instance == null){
            instance = new ExerciseDbmsDao();
        }
        return instance;
    }

    private static final String CALL_INSERT_EXERCISE = "{CALL insert_exercise(?,?)}";
    private static final String CALL_FIND_EXERCISE_BY_NAME = "{CALL find_exercise_by_name(?)}";
    private static final String CALL_FIND_ALL_EXERCISES = "{CALL find_all_exercises()}";
    private static final String CALL_FIND_BY_FOCUS = "{CALL find_exercise_by_focus(?)}";
    private static final String CALL_DELETE_EXERCISE = "{CALL delete_exercise(?)}";

    @Override
    public void insert(Exercise exercise, String focus){

        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            executeInsert(connection, exercise);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection connection, Exercise exercise) throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_INSERT_EXERCISE)){
            cs.setString(1, exercise.getName());
            cs.setString(2, exercise.getFocus());

            cs.execute();
        }
    }

    @Override
    public Exercise findBy(String name){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFind(connection, name);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private Exercise executeFind(Connection connection, String name)throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_FIND_EXERCISE_BY_NAME)){
            cs.setString(1, name);
            try(ResultSet rs = cs.executeQuery()){
                return mapExercise(rs);
            }
        }
    }

    private Exercise mapExercise(ResultSet rs) throws SQLException{

        if(!rs.next()){
            return null;
        }

        String name = rs.getString("name");
        String focus = rs.getString("focus");

        return new Exercise(
        name,
        focus);
    }

    @Override
    public Exercise findByFocus(String focus){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindFocus(connection, focus);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private Exercise executeFindFocus(Connection connection, String focus)throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_FIND_BY_FOCUS)){
            cs.setString(1, focus);
            try(ResultSet rs = cs.executeQuery()){
                return mapExercise(rs);
            }
        }
    }

    @Override
    public List<Exercise> findAll(){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindAll(connection);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private List<Exercise> executeFindAll(Connection connection) throws SQLException {

        List<Exercise> exercises = new ArrayList<>();

        try (CallableStatement cs = connection.prepareCall(CALL_FIND_ALL_EXERCISES);
            ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {

                String name = rs.getString("name");
                String focus = rs.getString("focus");

                Exercise exercise = new Exercise(
                    name,
                    focus);

                exercises.add(exercise);
            }
        }

        return exercises;
    }

    @Override
    public void delete(String name){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            try(CallableStatement cs = connection.prepareCall(CALL_DELETE_EXERCISE)){
                cs.setString(1, name);
                cs.execute();
            }
        }catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

}
