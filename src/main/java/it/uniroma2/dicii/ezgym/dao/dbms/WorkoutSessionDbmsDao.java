package it.uniroma2.dicii.ezgym.dao.dbms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.dao.interfacedao.WorkoutSessionDao;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutSession;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.DbmsConnector;


public class WorkoutSessionDbmsDao implements WorkoutSessionDao {

    private static final String CALL_INSERT_SESSION = "{CALL insert_session(?,?)}";
    private static final String CALL_FIND_SESSION_BY_DAY = "{CALL find_session_by_day(?)}";
    private static final String CALL_FIND_ALL_SESSIONS = "{CALL find_all_sessions()}";
    private static final String CALL_DELETE_SESSION = "{CALL delete_session(?)}";

    private static final String SQL_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id";

    
    @Override
    public int insert(WorkoutSession session, int sessionId) {
        try {
            return insertAndReturnId(session);
        } catch (PersistenceException e) {
            throw e;
        }
    }

   
    public int insertAndReturnId(WorkoutSession session) {
        try {
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeInsertAndReturnId(connection, session);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private int executeInsertAndReturnId(Connection connection, WorkoutSession session) throws SQLException {
        try (CallableStatement cs = connection.prepareCall(CALL_INSERT_SESSION)) {

            cs.setInt(1, session.getSessionId());
            cs.setString(2, session.getDayOfWeek());

            cs.execute();
        }

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_LAST_INSERT_ID)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("Impossibile recuperare LAST_INSERT_ID()");
    }

    @Override
    public WorkoutSession findBy(String dayOfWeek) {
        try {
            Connection connection = DbmsConnector.getInstance().getConnection();
            return executeFind(connection, dayOfWeek);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private WorkoutSession executeFind(Connection connection, String dayOfWeek) throws SQLException {
        try (CallableStatement cs = connection.prepareCall(CALL_FIND_SESSION_BY_DAY)) {
            cs.setString(1, dayOfWeek);
            try (ResultSet rs = cs.executeQuery()) {
                return mapSession(rs);
            }
        }
    }

    private WorkoutSession mapSession(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }

        int sessionId = rs.getInt("session_id");
        String dayOfWeek = rs.getString("day_of_week");

        return new WorkoutSession(
                sessionId,
                dayOfWeek,
                new ArrayList<>()
        );
    }

    public List<WorkoutSession> findAll() {
        try {
            Connection conn = DbmsConnector.getInstance().getConnection();
            List<WorkoutSession> sessions = new ArrayList<>();
            try (CallableStatement cs = conn.prepareCall(CALL_FIND_ALL_SESSIONS);
                 ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    sessions.add(new WorkoutSession(
                            rs.getInt("session_id"),
                            rs.getString("day_of_week"),
                            new ArrayList<>()
                    ));
                }
            }
            return sessions;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void delete(int sesionid) {
        try {
            Connection connection = DbmsConnector.getInstance().getConnection();
            try (CallableStatement cs = connection.prepareCall(CALL_DELETE_SESSION)) {
                cs.setInt(1, sesionid);
                cs.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
