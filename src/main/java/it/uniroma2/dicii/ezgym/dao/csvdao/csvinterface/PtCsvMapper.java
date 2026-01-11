package it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;

public interface PtCsvMapper {
    String[] toCsvFields(PersonalTrainer pt, UUID id);
    PersonalTrainer fromCsvFields(String[] fields);

    UUID getIdFromCsv(String[] fields);
    String getEmailFromCsv(String[] fields);
}
