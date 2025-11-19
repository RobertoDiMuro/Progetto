package it.uniroma2.dicii.ezgym.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbmsConnector{

    private static DbmsConnector instance;
    private Connection connection;
    Properties p = new Properties();
    String connectionUrl;
    String user;
    String password;

    public DbmsConnector(){

        try(InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties")){
            p.load(in);
            this.connectionUrl = p.getProperty("db.url");
            this.user = p.getProperty("db.user");
            this.password = p.getProperty("db.pass");
            Class .forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.connectionUrl, this.user, this.password);
        }catch(RuntimeException | SQLException |ClassNotFoundException |IOException _){
            //
        }
    }

    public static synchronized DbmsConnector getInstance(){
        if(instance == null){
            instance = new DbmsConnector();
        }
        return instance;
    }
    
    public synchronized Connection getConnection() {
        try {
            if ( connection.isClosed()) {
                connection = DriverManager.getConnection(connectionUrl, user, password);
            }
        } catch (SQLException _) {
           //
        }
        return this.connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException _) {
            //
        }
    }
}