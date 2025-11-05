package it.uniroma2.dicii.ezgym.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {
    private Meal meal;
    private Food food1;
    private Food food2;
    private List<Food> portions;
    private Macronutrients macro1;
    private Macronutrients macro2;

    @BeforeEach
    void setUp() {
        // Creo macronutrienti per 100g
        macro1 = new Macronutrients(20.0, 30.0, 10.0, 290.0); // Pollo
        macro2 = new Macronutrients(10.0, 50.0, 5.0, 285.0);  // Riso
        
        // Creo cibi con porzioni specifiche
        food1 = new Food("Pollo", "Proteine", macro1, 150.0, "");
        food2 = new Food("Riso", "Carboidrati", macro2, 100.0, "");
        
        // Creo lista di porzioni
        portions = new ArrayList<>();
        portions.add(food1);
        portions.add(food2);
        
        // Creo il pasto
        meal = new Meal(MealType.PRANZO, portions, "Pasto principale");
    }

    @Test
    @DisplayName("Test costruttore e getters")
    void testConstructorAndGetters() {
        assertNotNull(meal);
        assertEquals(MealType.PRANZO, meal.getType());
        assertEquals(2, meal.getPortions().size());
        assertEquals("", meal.getNotes()); // Il costruttore setta sempre ""
    }

    @Test
    @DisplayName("Test setType con valore valido")
    void testSetTypeValid() {
        meal.setType(MealType.COLAZIONE);
        assertEquals(MealType.COLAZIONE, meal.getType());
    }

    @Test
    @DisplayName("Test setType con valore null solleva eccezione")
    void testSetTypeNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> meal.setType(null)
        );
        assertEquals("il tipo di pasto non può essere nullo", exception.getMessage());
    }

    @Test
    @DisplayName("Test setNotes con valore valido")
    void testSetNotesValid() {
        meal.setNotes("Note importanti");
        assertEquals("Note importanti", meal.getNotes());
    }

    @Test
    @DisplayName("Test setNotes con null")
    void testSetNotesNull() {
        meal.setNotes(null);
        assertNull(meal.getNotes());
    }

    @Test
    @DisplayName("Test addPortion con cibo valido")
    void testAddPortionValid() {
        Macronutrients macro3 = new Macronutrients(5.0, 20.0, 3.0, 129.0);
        Food food3 = new Food("Verdure", "Vegetali", macro3, 200.0, "");
        
        meal.addPortion(food3);
        assertEquals(3, meal.getPortions().size());
        assertTrue(meal.getPortions().contains(food3));
    }

    @Test
    @DisplayName("Test addPortion con null solleva eccezione")
    void testAddPortionNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> meal.addPortion(null)
        );
        assertEquals("L'esercizio non deve essere nullo", exception.getMessage());
    }

    @Test
    @DisplayName("Test removePortion con cibo valido")
    void testRemovePortionValid() {
        meal.removePortion(food1);
        assertEquals(1, meal.getPortions().size());
        assertFalse(meal.getPortions().contains(food1));
    }

    @Test
    @DisplayName("Test removePortion con null solleva eccezione")
    void testRemovePortionNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> meal.removePortion(null)
        );
        assertEquals("La porzione non deve essere nulla", exception.getMessage());
    }

    @Test
    @DisplayName("Test getPortions restituisce lista immutabile")
    void testGetPortionsImmutable() {
        List<Food> retrievedPortions = meal.getPortions();
        assertThrows(UnsupportedOperationException.class, 
            () -> retrievedPortions.add(food1)
        );
    }

    @Test
    @DisplayName("Test calculateTotalMacronutrients")
    void testCalculateTotalMacronutrients() {
        // food1: 150g -> proteine=30, carbs=45, fats=15, cal=435
        // food2: 100g -> proteine=10, carbs=50, fats=5, cal=285
        // Totale: proteine=40, carbs=95, fats=20, cal=720
        
        Macronutrients total = meal.calculateTotalMacronutrients();
        
        assertEquals(40.0, total.getProteins(), 0.01);
        assertEquals(95.0, total.getCarbohydrates(), 0.01);
        assertEquals(20.0, total.getFats(), 0.01);
        assertEquals(720.0, total.getTotalCalories(), 0.01);
    }

    @Test
    @DisplayName("Test calculateTotalCalories")
    void testCalculateTotalCalories() {
        // Totale calorie: 435 + 285 = 720
        double totalCalories = meal.calculateTotalCalories();
        assertEquals(720.0, totalCalories, 0.01);
    }

    @Test
    @DisplayName("Test calculateTotalMacronutrients con pasto vuoto")
    void testCalculateTotalMacronutrientsEmpty() {
        Meal emptyMeal = new Meal(MealType.SPUNTINO, new ArrayList<>(), "");
        Macronutrients total = emptyMeal.calculateTotalMacronutrients();
        
        assertEquals(0.0, total.getProteins());
        assertEquals(0.0, total.getCarbohydrates());
        assertEquals(0.0, total.getFats());
        assertEquals(0.0, total.getTotalCalories());
    }

    @Test
    @DisplayName("Test calculateTotalCalories con pasto vuoto")
    void testCalculateTotalCaloriesEmpty() {
        Meal emptyMeal = new Meal(MealType.CENA, new ArrayList<>(), "");
        assertEquals(0.0, emptyMeal.calculateTotalCalories());
    }

    @Test
    @DisplayName("Test costruttore crea copia difensiva della lista")
    void testConstructorDefensiveCopy() {
        List<Food> originalList = new ArrayList<>();
        originalList.add(food1);
        
        Meal newMeal = new Meal(MealType.COLAZIONE, originalList, "");
        
        // Modifico la lista originale
        originalList.add(food2);
        
        // La lista del pasto non deve essere modificata
        assertEquals(1, newMeal.getPortions().size());
    }
}