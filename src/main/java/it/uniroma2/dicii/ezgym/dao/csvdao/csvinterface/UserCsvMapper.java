package it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface;

import it.uniroma2.dicii.ezgym.domain.model.User;

import java.util.UUID;

public interface UserCsvMapper {
    String[] toCsvFields(User user, UUID id);
    User fromCsvFields(String[] fields);

    UUID getIdFromCsv(String[] fields);
    String getEmailFromCsv(String[] fields);

    boolean isAthlete(User user);
}
