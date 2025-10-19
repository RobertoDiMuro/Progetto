package it.uniroma2.dicii.EzGym;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label bmiLabel;
    @FXML private Label weightLabel;
    @FXML private Label goalLabel;
    @FXML private Label activeWeeksLabel;
    @FXML private Label completedWorkoutsLabel;
    @FXML private Label dailyCaloriesLabel;
    @FXML private Button shoppingListBtn;
    @FXML private Button mealPlanBtn;
    @FXML private Button workoutPlanBtn;
    @FXML private Button goToWorkoutBtn;
    @FXML private Button goToDietBtn;

    private UserData userData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userData = UserData.getInstance();
        
        if (welcomeLabel != null) {
            welcomeLabel.setText("Benvenuto, " + Main.USERNAME + "!");
        }
        
        updateUserInfo();
        
        setupButtons();
    }

    private void updateUserInfo() {
        if (userData.hasWorkoutPlan()) {
            if (bmiLabel != null) {
                bmiLabel.setText(String.format(">%.1f", userData.getBmi()));
            }
            
            if (weightLabel != null) {
                weightLabel.setText(userData.getWeight() + " Kg");
            }
            
            if (goalLabel != null) {
                goalLabel.setText(userData.getGoal());
            }
            
            if (activeWeeksLabel != null) {
                activeWeeksLabel.setText(String.valueOf(userData.getActiveWeeks()));
            }
            
            if (completedWorkoutsLabel != null) {
                completedWorkoutsLabel.setText(String.valueOf(userData.getCompletedWorkouts()));
            }
            
            if (dailyCaloriesLabel != null) {
                dailyCaloriesLabel.setText(String.valueOf(userData.getDailyCalories()));
            }
        } else {
            if (bmiLabel != null) {
                bmiLabel.setText("-");
            }
            
            if (weightLabel != null) {
                weightLabel.setText("-");
            }
            
            if (goalLabel != null) {
                goalLabel.setText("Richiedi la tua scheda");
            }
            
            if (activeWeeksLabel != null) {
                activeWeeksLabel.setText("0");
            }
            
            if (completedWorkoutsLabel != null) {
                completedWorkoutsLabel.setText("0");
            }
            
            if (dailyCaloriesLabel != null) {
                dailyCaloriesLabel.setText("0");
            }
        }
    }

    private void setupButtons() {
        if (shoppingListBtn != null) {
            shoppingListBtn.setOnAction(e -> handleShoppingList());
        }
        
        if (mealPlanBtn != null) {
            mealPlanBtn.setOnAction(e -> handleMealPlan());
        }
        
        if (workoutPlanBtn != null) {
            workoutPlanBtn.setOnAction(e -> handleWorkoutPlan());
        }
        
        if (goToWorkoutBtn != null) {
            goToWorkoutBtn.setOnAction(e -> navigateToWorkout());
        }
        
        if (goToDietBtn != null) {
            goToDietBtn.setOnAction(e -> navigateToDiet());
        }
    }

    private void handleShoppingList() {
        System.out.println("Lista della spesa cliccata");
        showNotImplementedAlert("Lista della spesa");
    }

    private void handleMealPlan() {
        System.out.println("Programma pasti cliccato");
        showNotImplementedAlert("Programma pasti");
    }

    private void handleWorkoutPlan() {
        System.out.println("Programma allenamenti cliccato");
        showNotImplementedAlert("Programma allenamenti");
    }

    private void navigateToWorkout() {
        if (userData.hasWorkoutPlan()) {
            Navigator.setRoot("/fxml/Scheda.fxml");
        } else {
            Navigator.setRoot("/fxml/WorkoutRequest.fxml");
        }
    }

    private void navigateToDiet() {
        System.out.println("Navigazione alla dieta");
        showNotImplementedAlert("Dieta");
    }

    private void showNotImplementedAlert(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Funzionalità in sviluppo");
        alert.setHeaderText(null);
        alert.setContentText("La funzione '" + feature + "' sarà disponibile prossimamente.");
        alert.showAndWait();
    }

    public void refreshData() {
        updateUserInfo();
    }
}