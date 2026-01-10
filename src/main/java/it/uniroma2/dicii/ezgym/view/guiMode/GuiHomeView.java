package it.uniroma2.dicii.ezgym.view.guimode;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.controller.WorkoutController;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiHomeView {
    
    @FXML private Label activeWeeksLabel;

    @FXML private Label bmiLabel;

    @FXML private Label completedWorkoutsLabel;

    @FXML private Label dailyCaloriesLabel;

    @FXML private Button goToDietBtn;

    @FXML private Button goToWorkoutBtn;

    @FXML private Label goalLabel;

    @FXML private Button mealPlanBtn;

    @FXML private Label notImplementLabel;

    @FXML private Button shoppingListBtn;

    @FXML private Label weightLabel;

    @FXML private Label welcomeLabel;

    @FXML private Button workoutPlanBtn;

    private AthleteBean currAthlete;
   
   

    public void setAthlete(AthleteBean athlete){
        this.currAthlete = athlete;
        populateFields();
    }

    @FXML
    private void initialize(){
        if (notImplementLabel != null) notImplementLabel.setText("");
        populateFields();
    }

    private void populateFields(){
        if(currAthlete == null){
            return;
        }

        welcomeLabel.setText("Ciao, " + currAthlete.getName() + "!");
        
        String weight = (currAthlete.getWeight() > 0) ? String.valueOf(currAthlete.getWeight()) : "";
        String target = (currAthlete.getTarget() != null) ? String.valueOf(currAthlete.getTarget()) : "";
        String bmi = (weight.isEmpty() || currAthlete.getHeight() <= 0) ? "" : String.valueOf(calculateBMI());

        if(weight.isEmpty()){
            weightLabel.setText("");
        }else{
            weightLabel.setText("Peso: " + weight + "kg | ");
        }
        
        if(target.isEmpty()){
            goalLabel.setText("");
        }else{
            goalLabel.setText("Obiettivo: " + target);
        }

        if(bmi.isEmpty()){
            bmiLabel.setText("");
        }else{
            bmiLabel.setText("BMI: " + bmi + " | ");
        }
       
        
        activeWeeksLabel.setText("0");
        completedWorkoutsLabel.setText("0");
        dailyCaloriesLabel.setText("0");

        notImplementLabel.setText("");
    }


    @FXML
    private void onClickNotImplemented(){
        notImplementLabel.setText("Funzione non ancora implementata!");
    }

    @FXML
    private void onClickWorkoutRequest() {

        WorkoutController controller = new WorkoutController();
        WorkoutBean wb = controller.getWorkoutByAthleteName(currAthlete);

        Navigator.navigateTo("/fxml/VisualizzaScheda.fxml", c -> {
            GuiWorkoutView view = (GuiWorkoutView) c;
            view.setAthlete(currAthlete);
            view.setWorkout(wb);
        });
        
    }

    private double calculateBMI() {
        double heightInMeters = currAthlete.getHeight() / 100.0;
        double bmi = currAthlete.getWeight() / (heightInMeters * heightInMeters);
        return Math.round(bmi * 100.0) / 100.0;
    }


}
