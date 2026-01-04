package it.uniroma2.dicii.ezgym.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbmsConnector {

    private static DbmsConnector instance;
    private Connection connection;
    private String connectionUrl;
    private String user;
    private String password;

    private DbmsConnector() {
        loadProperties();
        loadDriver();
        openConnection();
    }

   
    private void loadProperties() {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("database.properties")) {

            if (in == null) {
                throw new RuntimeException("ERRORE: File 'database.properties' non trovato nel classpath!");
            }

            Properties props = new Properties();
            props.load(in);

            this.connectionUrl = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.pass");

            if (connectionUrl == null || user == null || password == null) {
                throw new RuntimeException("ERRORE: Parametri DB mancanti in database.properties");
            }

            System.out.println(
                "Caricate proprietà DB → url="
                + connectionUrl + ", user=" + user
            );

        } catch (IOException e) {
            throw new RuntimeException("ERRORE durante il caricamento di database.properties", e);
        }
    }

    
    private void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL registrato correttamente.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ERRORE: Driver MySQL non trovato!", e);
        }
    }

   
    private void openConnection() {
        try {
            this.connection = DriverManager.getConnection(connectionUrl, user, password);
            System.out.println("Connessione al database stabilita con successo.");
        } catch (SQLException e) {
            throw new RuntimeException("ERRORE FATALE: impossibile connettersi al database!", e);
        }
    }

    
    public static synchronized DbmsConnector getInstance() {
        if (instance == null) {
            instance = new DbmsConnector();
        }
        return instance;
    }

   
    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.err.println("Connessione chiusa o nulla → riapertura...");
                openConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il controllo dello stato della connessione", e);
        }
        return connection;
    }

   
    public synchronized void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connessione al DB chiusa correttamente.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
