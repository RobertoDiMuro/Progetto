package it.uniroma2.dicii.ezgym.dao.csvdao;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.PtCsvMapper;
import it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv.DefaultPtCsvMapper;
import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

import java.io.*;
import java.util.*;

public class PersonalTrainerCsvDao implements PersonalTrainerDao {

    private static final String SEP = ";";
    private static final String DEFAULT_CSV_PATH = "src/data/csv/personal_trainers.csv";
    private static PersonalTrainerCsvDao instance;

    public static synchronized PersonalTrainerCsvDao getInstance(){
        if(instance == null){
            instance = new PersonalTrainerCsvDao();
        }
        return instance;
    }

    private final String filePath;
    private final PtCsvMapper mapper;

    public PersonalTrainerCsvDao() {
        this(DEFAULT_CSV_PATH, new DefaultPtCsvMapper());
    }

    public PersonalTrainerCsvDao(String filePath, PtCsvMapper mapper) {
        this.filePath = Objects.requireNonNull(filePath, "filePath");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File f = new File(filePath);
            File parent = f.getParentFile();

            if (parent != null && !parent.exists()) {
                boolean dirsCreated = parent.mkdirs();
                if (!dirsCreated && !parent.exists()) {
                    throw new PersistenceException(
                            "Impossibile creare la cartella CSV: " + parent.getAbsolutePath()
                    );
                }
            }

            if (!f.exists()) {
                boolean created = f.createNewFile();
                if (!created && !f.exists()) {
                    throw new PersistenceException("Impossibile creare il file CSV: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la creazione del file CSV: " + filePath, e);
        }
    }


    private void appendLine(String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Errore durante l'append sul file CSV", e);
        }
    }

    private List<String> readAllRawLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                lines.add(line);
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
        return lines;
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

    @Override
    public void insert(PersonalTrainer personalTrainer, UUID id) {
        String[] fields = mapper.toCsvFields(personalTrainer, id);
        appendLine(String.join(SEP, fields));
    }

    @Override
    public PersonalTrainer findBy(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] arr = line.split(SEP, -1);
                if (email.equals(mapper.getEmailFromCsv(arr))) {
                    return mapper.fromCsvFields(arr);
                }
            }
            throw new NoSuchElementException("Nessun PersonalTrainer trovato con email=" + email);
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file CSV", e);
        }
    }

    @Override
    public void delete(UUID id) {
        List<String> kept = new ArrayList<>();

        for (String line : readAllRawLines()) {
            String[] arr = line.split(SEP, -1);
            if (!id.equals(mapper.getIdFromCsv(arr))) {
                kept.add(line);
            }
        }

        writeAll(kept);
    }
}
