package it.uniroma2.dicii.ezgym.dao.csvdao.defaultcsv;

import java.util.UUID;

import it.uniroma2.dicii.ezgym.dao.csvdao.csvinterface.AthleteCsvMapper;
import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;

public class DefaultAthleteCsvMapper implements AthleteCsvMapper {


    @Override
    public String[] toCsvFields(Athlete athlete, UUID id) {
        UUID effectiveId = requireId(athlete, id);

        if (athlete == null) {
            return new String[] {
                    effectiveId.toString(),
                    "",
                    "0",
                    "0",
                    "0",
                    "",
                    "",
                    "",
                    "false"
            };
        }

        return new String[] {
                effectiveId.toString(),
                nn(athlete.getGender()),
                String.valueOf(athlete.getAge()),
                String.valueOf(athlete.getWeight()),
                String.valueOf(athlete.getHeight()),
                enumName(athlete.getTarget()),
                enumName(athlete.getActivityLevel()),
                enumName(athlete.getWorkoutDay()),
                String.valueOf(athlete.getIsWorkoutRequested())
        };
    }


    @Override
    public Athlete fromCsvFields(String[] f) {
        Athlete a = new Athlete();

        UUID id = getIdFromCsv(f);
        a.setId(id);

        a.setGender(get(f, 1));
        a.setAge(parseInt(get(f, 2), 0));
        a.setWeight(parseDouble(get(f, 3), 0));
        a.setHeight(parseDouble(get(f, 4), 0));

        String targetStr = get(f, 5);
        if (!targetStr.isEmpty()) {
            try { a.setTarget(Target.valueOf(targetStr)); } catch (IllegalArgumentException _) { 
                //
             }
        }

        String activityStr = get(f, 6);
        if (!activityStr.isEmpty()) {
            try { a.setActivityLevel(ActivityLevel.valueOf(activityStr)); } catch (IllegalArgumentException _) { 
                //
            }
        }

        String workoutDayStr = get(f, 7);
        if (!workoutDayStr.isEmpty()) {
            try { a.setWorkoutDay(WorkoutDay.valueOf(workoutDayStr)); } catch (IllegalArgumentException _) { 
                //
            }
        }

        a.setIsWorkoutRequested(parseBoolean(get(f, 8), false));

        return a;
    }

    @Override
    public UUID getIdFromCsv(String[] fields) {
        String raw = get(fields, 0);
        if (raw.isEmpty()) return null;
        try {
            return UUID.fromString(raw);
        } catch (IllegalArgumentException _) {
            return null;
        }
    }

    private static String get(String[] arr, int i) {
        if (arr == null || i < 0 || i >= arr.length) return "";
        return arr[i] == null ? "" : arr[i].trim();
    }

    private static String nn(String s) {
        return s == null ? "" : s;
    }

    private static int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception _) { return def; }
    }

    private static double parseDouble(String s, double def) {
        try { return Double.parseDouble(s); } catch (Exception _) { return def; }
    }

    private static boolean parseBoolean(String s, boolean def) {
        if (s == null) return def;
        String v = s.trim().toLowerCase();
        if (v.equals("true") || v.equals("1") || v.equals("yes") || v.equals("y")) return true;
        if (v.equals("false") || v.equals("0") || v.equals("no") || v.equals("n")) return false;
        return def;
    }

    private static UUID requireId(Athlete athlete, UUID id) {
        UUID effectiveId = id;
        if (effectiveId == null && athlete != null) {
            effectiveId = athlete.getId();
        }
        if (effectiveId == null) {
            throw new IllegalArgumentException("id nullo: passa id a insert() oppure valorizza athlete.setId()");
        }
        return effectiveId;
    }

    private static String enumName(Enum<?> e) {
        return e == null ? "" : e.name();
    }
}
