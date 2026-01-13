package it.uniroma2.dicii.ezgym.exercisetest;

import it.uniroma2.dicii.ezgym.domain.model.Exercise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;



class ExerciseTest {

    @Test
    void defaultConstructor_shouldCreateObjectWithNullFields() {
        Exercise exercise = new Exercise();

        assertNotNull(exercise);
        assertNull(exercise.getName());
        assertNull(exercise.getFocus());
    }

    @Test
    void parameterizedConstructor_shouldSetFields() {
        Exercise exercise = new Exercise("Squat", "Legs");

        assertEquals("Squat", exercise.getName());
        assertEquals("Legs", exercise.getFocus());
    }

    @Test
    void setters_shouldUpdateFields() {
        Exercise exercise = new Exercise();

        exercise.setName("Bench Press");
        exercise.setFocus("Chest");

        assertEquals("Bench Press", exercise.getName());
        assertEquals("Chest", exercise.getFocus());
    }

}
