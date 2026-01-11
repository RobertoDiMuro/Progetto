package it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.domain.model.Athlete;

public interface AthleteCsvMapper {

    String[] toCsvFields(Athlete athlete, UUID id);

    Athlete fromCsvFields(String[] fields);

    UUID getIdFromCsv(String[] fields);
}
