package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;

public class PersonalTrainerDbmsDao implements PersonalTrainerDao {

    

    private static final String CALL_INSERT_PT = "{CALL insert_pt(?,?)}";
    private static final String CALL_FIND_PT_BY_EMAIL = "{CALL find_pt_by_email(?)}";
    private static final String CALL_DELETE_PT = "{CALL delete_pt(?)}";

    @Override
    public void insert(PersonalTrainer personalTrainer, UUID id){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            executeInsert(connection, personalTrainer);
        }catch(Exception e){
            throw new PersistenceException(e);
        }
    }

    private void executeInsert(Connection connection, PersonalTrainer personalTrainer) throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_INSERT_PT)){
            cs.setString(1, personalTrainer.getId().toString());
            cs.setDouble(2, personalTrainer.getActiveUsers());

            cs.execute();
        }
    }

    @Override
    public PersonalTrainer findBy(String email){
        try{
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFindByEmail(connection, email);
        }catch(Exception e){
            throw new PersistenceException(e);
        }
    }

    private PersonalTrainer executeFindByEmail(Connection connection, String email) throws SQLException{

        try(CallableStatement cs = connection.prepareCall(CALL_FIND_PT_BY_EMAIL)){
            cs.setString(1, email);

            try(ResultSet rs = cs.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                return mapPersonalTrainer(rs);
            }
        }
    }

    private PersonalTrainer mapPersonalTrainer(ResultSet rs) throws SQLException{

        UUID id = UUID.fromString(rs.getString("id_pt"));
        double activeUsers = rs.getDouble("active_users");

        PersonalTrainer personalTrainer = new PersonalTrainer();

        personalTrainer.setId(id);
        personalTrainer.setActiveUsers(activeUsers);

        return personalTrainer;
    }

    @Override
    public void delete(UUID id){
        try(Connection connection = DbmsConnector.getInstance().getConnection();
            CallableStatement cs = connection.prepareCall(CALL_DELETE_PT)){

            cs.setString(1, id.toString());
            cs.execute();
        }catch(SQLException e){
            throw new PersistenceException(e);
        }
    }
    
}
