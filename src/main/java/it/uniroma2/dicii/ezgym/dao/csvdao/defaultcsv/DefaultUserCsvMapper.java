package it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.UserCsvMapper;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;

public class DefaultUserCsvMapper implements UserCsvMapper {
    
    @Override
    public String[] toCsvFields(User user, UUID id) {
        UUID effectiveId = (id != null) ? id : user.getId();
        if (effectiveId == null) {
            throw new IllegalArgumentException("id nullo: passa id a insert() oppure valorizza user.setId()");
        }
        
        user.setId(effectiveId);

        return new String[]{
                effectiveId.toString(),
                nn(user.getName()),
                nn(user.getSurname()),
                nn(user.getEmail()),
                nn(user.getPassword()),
                user.getRole() != null ? user.getRole().name() : ""
        };
    }

    @Override
    public User fromCsvFields(String[] f) {
        UUID id = UUID.fromString(get(f, 0));
        String name = get(f, 1);
        String surname = get(f, 2);
        String email = get(f, 3);
        String password = get(f, 4);

        Role role = null;
        String roleStr = get(f, 5);
        if (!roleStr.isEmpty()) {
            role = Role.valueOf(roleStr);
        }

        return new User(id, name, surname, email, password, role) {};
    }

    @Override
    public UUID getIdFromCsv(String[] fields) {
        return UUID.fromString(get(fields, 0));
    }

    @Override
    public String getEmailFromCsv(String[] fields) {
        return get(fields, 3);
    }

    @Override
    public boolean isAthlete(User user) {
        if (user == null || user.getRole() == null) return false;
        String r = user.getRole().toString();
        return "ATHLETE".equalsIgnoreCase(r) || "ATLETA".equalsIgnoreCase(r);
    }

    private static String get(String[] arr, int i) {
        if (arr == null || i < 0 || i >= arr.length) return "";
        return arr[i] == null ? "" : arr[i].trim();
    }

    private static String nn(String s) {
        return s == null ? "" : s;
    }
}


