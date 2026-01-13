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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.AthleteCsvMapper;
import it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv.DefaultAthleteCsvMapper;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

public class AthleteCsvDao implements AthleteDao {

    private static final String SEP = ";";
    private static final String DEFAULT_CSV_PATH = "src/data/csv/athlete.csv";
    private static AthleteCsvDao instance;

    public static synchronized AthleteCsvDao getInstance(){
        if(instance == null){
            instance = new AthleteCsvDao();
        }
        return instance;
    }

    private final String filePath;
    private final AthleteCsvMapper mapper;

    private final UserCsvDao userCsvDao;

    public AthleteCsvDao() {
        this(DEFAULT_CSV_PATH, new DefaultAthleteCsvMapper(), new UserCsvDao());
    }

    public AthleteCsvDao(String filePath, AthleteCsvMapper mapper, UserCsvDao userCsvDao) {
        this.filePath = Objects.requireNonNull(filePath, "filePath");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.userCsvDao = Objects.requireNonNull(userCsvDao, "userCsvDao");
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

    private Athlete findAthleteById(UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] arr = line.split(SEP, -1);
                UUID csvId = mapper.getIdFromCsv(arr);

                if (id != null && id.equals(csvId)) {
                    return mapper.fromCsvFields(arr);
                }
            }
            return null;
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
    }

    private static void applyUserFields(Athlete athlete, User user) {
        if (athlete == null || user == null) return;

        athlete.setId(user.getId());
        athlete.setName(user.getName());
        athlete.setSurname(user.getSurname());
        athlete.setEmail(user.getEmail());
        athlete.setPassword(user.getPassword());

        athlete.setRole(user.getRole());
    }

    @Override
    public void insert(Athlete athlete, UUID id) {
        String[] fields = mapper.toCsvFields(athlete, id);
        appendLine(String.join(SEP, fields));
    }

    @Override
    public Athlete findBy(String email) {
        if (email == null) return null;
        String normalized = email.trim().toLowerCase();

        User user;
        try {
            user = userCsvDao.findByEmail(normalized);
        } catch (NoSuchElementException _) {
            user = null;
        }

        if (user == null) return null;
        if (user.getRole() == null || user.getRole() != Role.ATHLETE) return null;

        Athlete athlete = findAthleteById(user.getId());
        if (athlete == null) {
            athlete = new Athlete();
            athlete.setId(user.getId());
        }

        applyUserFields(athlete, user);
        return athlete;
    }

    @Override
    public List<Athlete> findAll() {
        List<Athlete> out = new ArrayList<>();

        List<User> users = userCsvDao.findAll();
        for (User u : users) {
            if (u != null && u.getRole() == Role.ATHLETE) {

                Athlete a = findAthleteById(u.getId());
                if (a == null) {
                    a = new Athlete();
                    a.setId(u.getId());
                }

                applyUserFields(a, u);
                out.add(a);
            }
        }

        return out;
    }


    @Override
    public void update(UUID id, Athlete athlete) {
        if (id == null) return;

        List<String> lines = new ArrayList<>();
        for (String line : readAllRawLines()) {
            String[] arr = line.split(SEP, -1);
            UUID csvId = mapper.getIdFromCsv(arr);
            if (!id.equals(csvId)) {
                lines.add(line);
            }
        }

        String[] fields = mapper.toCsvFields(athlete, id);
        lines.add(String.join(SEP, fields));

        writeAll(lines);
    }

    @Override
    public void closeRequest(UUID id) {
        if (id == null) return;

        Athlete a = findAthleteById(id);
        if (a == null) return;

        a.setIsWorkoutRequested(false);
        update(id, a);
    }

    @Override
    public void delete(UUID id) {
        if (id == null) return;

        List<String> lines = new ArrayList<>();
        for (String line : readAllRawLines()) {
            String[] arr = line.split(SEP, -1);
            UUID csvId = mapper.getIdFromCsv(arr);
            if (!id.equals(csvId)) {
                lines.add(line);
            }
        }
        writeAll(lines);

        try {
            userCsvDao.delete(id);
        } catch (Exception _) {
            //
        }
    }
}
