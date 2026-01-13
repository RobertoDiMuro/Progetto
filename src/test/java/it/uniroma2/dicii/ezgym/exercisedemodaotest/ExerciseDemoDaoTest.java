package it.uniroma2.dicii.ezgym.exercisedemodaotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma2.dicii.ezgym.dao.demodao.ExerciseDemoDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;

public class ExerciseDemoDaoTest {
    private ExerciseDemoDao dao;
    private Map<String, Exercise> table; 

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        dao = new ExerciseDemoDao();


        Field f = ExerciseDemoDao.class.getDeclaredField("exerciseTable");
        f.setAccessible(true);
        table = (Map<String, Exercise>) f.get(dao);
        table.clear();
    }

    @Test
    void getInstance_shouldReturnSameSingletonInstance() {
        ExerciseDemoDao a = ExerciseDemoDao.getInstance();
        ExerciseDemoDao b = ExerciseDemoDao.getInstance();
        assertSame(a, b);
    }

    @Test
    void insert_whenExerciseIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dao.insert(null, "Legs")
        );
        assertTrue(ex.getMessage().toLowerCase().contains("nullo"));
    }

    @Test
    void insert_shouldStoreExerciseUsingFocusAsKey() {
        Exercise e = new Exercise("Squat", "Legs");

        dao.insert(e, "Legs");

        assertEquals(1, table.size());
        assertSame(e, table.get("Legs"));
    }

    @Test
    void findByFocus_whenFocusIsNull_shouldReturnNull() {
        assertNull(dao.findByFocus(null));
    }

    @Test
    void findByFocus_whenPresent_shouldReturnMatchingExercise() {
        Exercise e1 = new Exercise("Squat", "Legs");
        Exercise e2 = new Exercise("Bench Press", "Chest");

        dao.insert(e1, "Legs");
        dao.insert(e2, "Chest");

        Exercise found = dao.findByFocus("Chest");
        assertNotNull(found);
        assertEquals("Bench Press", found.getName());
        assertEquals("Chest", found.getFocus());
    }

    @Test
    void findBy_whenNameIsNull_shouldReturnNull() {
        assertNull(dao.findBy(null));
    }

    @Test
    void findBy_whenPresent_shouldReturnMatchingExercise() {
        Exercise e1 = new Exercise("Squat", "Legs");
        Exercise e2 = new Exercise("Deadlift", "Back");

        dao.insert(e1, "Legs");
        dao.insert(e2, "Back");

        Exercise found = dao.findBy("Deadlift");
        assertNotNull(found);
        assertEquals("Deadlift", found.getName());
        assertEquals("Back", found.getFocus());
    }

    @Test
    void findAll_shouldReturnAllStoredExercises() {
        dao.insert(new Exercise("Squat", "Legs"), "Legs");
        dao.insert(new Exercise("Bench Press", "Chest"), "Chest");

        List<Exercise> all = dao.findAll();
        assertEquals(2, all.size());

        assertTrue(all.stream().anyMatch(e -> "Squat".equals(e.getName())));
        assertTrue(all.stream().anyMatch(e -> "Bench Press".equals(e.getName())));
    }

    @Test
    void delete_shouldRemoveEntryByKeyPassed() {
       
        dao.insert(new Exercise("Squat", "Legs"), "Legs");
        assertEquals(1, dao.findAll().size());

        dao.delete("Legs");

        assertEquals(0, dao.findAll().size());
        assertNull(dao.findByFocus("Legs"));
    }
}

