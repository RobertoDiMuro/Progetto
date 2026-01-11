package it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.PtCsvMapper;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;

import java.util.UUID;

public class DefaultPtCsvMapper implements PtCsvMapper {

    private static final int IDX_ID = 0;
    private static final int IDX_NAME = 1;
    private static final int IDX_SURNAME = 2;
    private static final int IDX_EMAIL = 3;
    private static final int IDX_PASSWORD = 4;
    private static final int IDX_ROLE = 5;
    private static final int IDX_ACTIVE_USERS = 6;

    public String[] toCsvFields(PersonalTrainer pt, UUID id) {
        UUID effectiveId = (id != null) ? id : pt.getId();
        if (effectiveId == null) {
            throw new IllegalArgumentException("id nullo: passa id a insert() oppure valorizza pt.setId()");
        }
        pt.setId(effectiveId);

        return new String[]{
                effectiveId.toString(),
                nn(pt.getName()),
                nn(pt.getSurname()),
                nn(pt.getEmail()),
                nn(pt.getPassword()),
                pt.getRole() != null ? pt.getRole().name() : "",
                Double.toString(pt.getActiveUsers())
        };
    }

    public PersonalTrainer fromCsvFields(String[] f) {
        if (f == null || f.length < 6) {
            throw new PersistenceException("Riga CSV non valida per PersonalTrainer", null);
        }

        UUID id = UUID.fromString(get(f, IDX_ID));
        String name = get(f, IDX_NAME);
        String surname = get(f, IDX_SURNAME);
        String email = get(f, IDX_EMAIL);
        String password = get(f, IDX_PASSWORD);

        Role role = null;
        String roleStr = get(f, IDX_ROLE);
        if (!roleStr.isEmpty()) {
            role = Role.valueOf(roleStr);
        }

        double activeUsers = 0.0;
        String auStr = (f.length > IDX_ACTIVE_USERS) ? get(f, IDX_ACTIVE_USERS) : "";
        if (!auStr.isEmpty()) {
            try {
                activeUsers = Double.parseDouble(auStr);
            } catch (NumberFormatException ignored) {
                activeUsers = 0.0;
            }
        }

        PersonalTrainer pt = new PersonalTrainer();
        pt.setId(id);
        pt.setName(name);
        pt.setSurname(surname);
        pt.setEmail(email);
        pt.setPassword(password);
        pt.setRole(role);
        pt.setActiveUsers(activeUsers);

        return pt;
    }

    public UUID getIdFromCsv(String[] fields) {
        return UUID.fromString(get(fields, IDX_ID));
    }

    public String getEmailFromCsv(String[] fields) {
        return get(fields, IDX_EMAIL);
    }

    private static String get(String[] arr, int i) {
        if (arr == null || i < 0 || i >= arr.length || arr[i] == null) return "";
        return arr[i].trim();
    }

    private static String nn(String s) {
        return s == null ? "" : s;
    }
}
