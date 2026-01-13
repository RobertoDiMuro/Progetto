package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class UserDbmsDao implements UserDao{

    private static UserDbmsDao instance;

    public static synchronized UserDbmsDao getInstance(){
        if(instance == null){
            instance = new UserDbmsDao();
        }
        return instance;
    }

    private static final String CALL_INSERT_USER = "{CALL insert_user(?,?,?,?,?,?)}";
    private static final String CALL_FIND_USER_BY_ID = "{CALL find_user_by_id(?)}";   
    private static final String CALL_FIND_USER_BY_EMAIL = "{CALL find_user_by_email(?)}";
    private static final String CALL_COUNT_ATHLETES = "{CALL count_athletes()}";
    private static final String CALL_FIND_ALL_USERS = "{CALL find_all_users()}";
    private static final String CALL_DELETE_USER = "{CALL delete_user(?)}";
    
    @Override
    public void insert(User user, UUID id){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            executeInsert(connection, user);
        }catch(Exception e){
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection connection, User user) throws SQLException{
        try(CallableStatement cs = connection.prepareCall(CALL_INSERT_USER)){
            cs.setString(1, user.getId().toString());
            cs.setString(2, user.getName());
            cs.setString(3, user.getSurname());
            cs.setString(4, user.getEmail());
            cs.setString(5, user.getPassword());
            cs.setString(6, user.getRole().name());

            cs.execute();
        }
    }

    @Override
    public User findById(UUID id){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindById(connection, id);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private User executeFindById(Connection connection, UUID id)throws SQLException{
        try(CallableStatement cs = connection.prepareCall(CALL_FIND_USER_BY_ID)){
            cs.setString(1, id.toString());

            try(ResultSet rs = cs.executeQuery()){
                if(!rs.next()){
                    return null;                   
                }
                return mapUser(rs);
            }
        }
    }

    private User mapUser(ResultSet rs) throws SQLException{

        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String password = rs.getString("password");
        
        Role role;
        try{
            role = Role.valueOf(rs.getString("role"));
        }catch(Exception _){
            throw new PersistenceException("Ruolo cercato non valido: " + rs.getString("role"));
        }

        User user;

        switch (role) {
            case ATHLETE:
                user = new Athlete();
                break;
            case PERSONAL_TRAINER:
                user = new PersonalTrainer();
                break;
            default:
                throw new SQLException("Ruolo utente sconosciuto: " + role);
        }

        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        
        return user;
    }

    @Override
    public User findByEmail(String email){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindByEmail(connection, email);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private User executeFindByEmail(Connection connection, String email) throws SQLException {
    try (CallableStatement cs = connection.prepareCall(CALL_FIND_USER_BY_EMAIL)) {

        cs.setString(1, email);

        try (ResultSet rs = cs.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            return mapUser(rs);
        }
    }
}

    @Override
    public int countAthletes() throws PersistenceException {
        try (Connection conn = DbmsConnector.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(CALL_COUNT_ATHLETES);
            ResultSet rs = cs.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;

        } catch (SQLException e) {
            throw new PersistenceException("Errore durante l'esecuzione di count_athletes()", e);
        }
    }



    @Override
    public List<User> findAll(){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindAll(connection);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    private List<User> executeFindAll(Connection connection) throws SQLException{

        List<User> users = new ArrayList<>();
        try(CallableStatement cs = connection.prepareCall(CALL_FIND_ALL_USERS)){
            try(ResultSet rs = cs.executeQuery()){
                while(rs.next()){
                    users.add(mapUser(rs));
                }
                return users;
            }  
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        try(Connection connection = DbmsConnector.getInstance().getConnection()){
            CallableStatement cs = connection.prepareCall(CALL_DELETE_USER);

            cs.setString(1, id.toString());
            cs.execute();

        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }
}
