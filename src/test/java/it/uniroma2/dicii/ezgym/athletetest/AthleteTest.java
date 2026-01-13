package it.uniroma2.dicii.ezgym.athletetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.UUID;

import org.junit.jupiter.api.Test;

import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.AthleteParams;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;

class AthleteTest {
    
    @Test
    void defaultConstructor_shouldCreateObjectWithDefaultValues(){
        Athlete a = new Athlete();

        assertNotNull(a);
        assertNull(a.getGender());
        assertEquals(0, a.getAge());
        assertEquals(0.0, a.getWeight());
        assertEquals(0.0, a.getHeight());
        assertNull(a.getTarget());
        assertNull(a.getActivityLevel());
        assertNull(a.getWorkoutDay());
        assertFalse(a.getIsWorkoutRequested());
    }

    @Test
    void setters_shouldUpdateFields(){
        Athlete a = new Athlete();

        a.setGender("Maschio");
        a.setAge(25);
        a.setWeight(80.0);
        a.setHeight(180.0);

        Target target = Target.values()[0];
        ActivityLevel activityLevel = ActivityLevel.values()[0];
        WorkoutDay workoutDay = WorkoutDay.values()[0];

        a.setTarget(target);
        a.setActivityLevel(activityLevel);
        a.setWorkoutDay(workoutDay);
        a.setIsWorkoutRequested(true);

        assertEquals("Maschio", a.getGender());
        assertEquals(25, a.getAge());
        assertEquals(80.0, a.getWeight(), 0.000001);
        assertEquals(180.0, a.getHeight(), 0.000001);
        assertEquals(target, a.getTarget());
        assertEquals(activityLevel, a.getActivityLevel());
        assertEquals(workoutDay, a.getWorkoutDay());
        assertTrue(a.getIsWorkoutRequested());
    }

    @Test
    void fullConstructor_shouldSetAthleteFieldsFromParams(){
        UUID id = UUID.randomUUID();

        Role role = Role.values()[0];
        Target target = Target.values()[0];
        ActivityLevel activityLevel = ActivityLevel.values()[0];
        WorkoutDay workoutDay = WorkoutDay.values()[0];

        AthleteParams params = new AthleteParams(
                "Femmina",
                30,
                65.5,
                170.0,
                target,
                activityLevel,
                workoutDay,
                true
        );

        Athlete athlete = new Athlete(
                id,
                "Anna",
                "Verdi",
                "anna@example.com",
                "secret",
                role,
                params
        );

        assertEquals("Femmina", athlete.getGender());
        assertEquals(30, athlete.getAge());
        assertEquals(65.5, athlete.getWeight(), 0.000001);
        assertEquals(170.0, athlete.getHeight(), 0.000001);
        assertEquals(target, athlete.getTarget());
        assertEquals(activityLevel, athlete.getActivityLevel());
        assertEquals(workoutDay, athlete.getWorkoutDay());
        assertTrue(athlete.getIsWorkoutRequested());
    }

    @Test
    void calculateBMI_shouldComputeAndRoundTo2Decimals(){
        Athlete athlete = new Athlete();

        athlete.setWeight(80.0);
        athlete.setHeight(180.0);

        assertEquals(24.69, athlete.calculateBMI(), 0.000001);
    }

    
}
