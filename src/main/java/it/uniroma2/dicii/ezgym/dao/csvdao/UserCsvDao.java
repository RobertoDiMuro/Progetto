package it.uniroma2.dicii.ezgym.dao.csvdao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.UserCsvMapper;
import it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv.DefaultUserCsvMapper;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class UserCsvDao implements UserDao{

    private static final String SEP = ";";

    private static final String DEFAULT_CSV_PATH = "src/data/csv/users.csv";
    private static UserCsvDao instance;

    public static synchronized UserCsvDao getInstance(){
        if(instance == null){
            instance = new UserCsvDao();
        }
        return instance;
    }

    private final String filePath;
    private final UserCsvMapper mapper;

    public UserCsvDao(){
        this(DEFAULT_CSV_PATH, new DefaultUserCsvMapper());
    }

    public UserCsvDao(String filePath, UserCsvMapper mapper) {
        this.filePath = Objects.requireNonNull(filePath, "filePath");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            Path p = Path.of(filePath);
            if (p.getParent() != null) {
                Files.createDirectories(p.getParent());
            }
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la creazione del file CSV: " + filePath, e);
        }
    }

    private void writeAll(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la scrittura del file CSV", e);
        }
    }

    private List<String> readAllRawLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                lines.add(line);
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
        return lines;
    }

    private void appendLine(String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Errore durante l'append sul file CSV", e);
        }
    }

    @Override
    public void insert(User user, UUID id) {

        String[] fields = mapper.toCsvFields(user, id);
        appendLine(String.join(SEP, fields));
    }

    @Override
    public User findById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] arr = line.split(SEP, -1);

                UUID csvId = mapper.getIdFromCsv(arr);
                if (id.equals(csvId)) {
                    return mapper.fromCsvFields(arr);
                }
            }
            return null;
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] arr = line.split(SEP, -1);

                String csvEmail = mapper.getEmailFromCsv(arr);
                if (email.equalsIgnoreCase(csvEmail == null ? "" : csvEmail.trim())) {
                    return mapper.fromCsvFields(arr);
                }
            }
            return null; 
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
    }

    @Override
    public int countAthletes() {
        int count = 0;
        for (User u : findAll()) {
            if (mapper.isAthlete(u)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (String line : readAllRawLines()) {
            String[] arr = line.split(SEP, -1);
            users.add(mapper.fromCsvFields(arr));
        }
        return users;
    }

    @Override
    public void delete(UUID id) {
        List<String> lines = new ArrayList<>();

        for (String line : readAllRawLines()) {
            String[] arr = line.split(SEP, -1);

            UUID csvId = mapper.getIdFromCsv(arr);
            if (!id.equals(csvId)) {
                lines.add(line);
            }
        }

        writeAll(lines);
    }

    
}
