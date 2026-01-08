package it.uniroma2.dicii.ezgym.view.guiMode;


import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.controller.WorkoutRequestController;
import it.uniroma2.dicii.ezgym.domain.model.ActivityLevel;
import it.uniroma2.dicii.ezgym.domain.model.Target;
import it.uniroma2.dicii.ezgym.domain.model.WorkoutDay;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

public class GuiWorkoutRequestView {
     
    @FXML private TextField ageField;

    @FXML private ToggleButton bulkButton;

    @FXML private ComboBox<ActivityLevel> currLevel;

    @FXML private ToggleButton cutButton;

    @FXML private ToggleButton femaleButton;

    @FXML private ToggleButton fiveButton;

    @FXML private ToggleButton fourButton;

    @FXML private ImageView goToHome;

    @FXML private TextField heightField;

    @FXML private ToggleButton maleButton;

    @FXML private ToggleButton mantainButton;

    @FXML private Button requestButton;

    @FXML private ToggleButton sixButton;

    @FXML private ToggleButton threeButton;

    @FXML private ToggleButton toneButton;

    @FXML private ToggleButton twoButton;

    @FXML private TextField weightField;

    @FXML private Label errorLabel;

    private AthleteBean currAthlete;
    private final WorkoutRequestController workoutRequestController = new WorkoutRequestController();

    public void setAthlete(AthleteBean athlete) {
        this.currAthlete = athlete;
    }

    @FXML
    private void initialize(){
       currLevel.getItems().setAll(ActivityLevel.values());

       configureGenderButton();
       configureTargetButton();
       configureWorkoutDayButton();
    }

    private void configureGenderButton(){
        maleButton.setOnAction(e -> {femaleButton.setSelected(false);});
        femaleButton.setOnAction(e -> {maleButton.setSelected(false);});
    }

    private void configureTargetButton(){
        ToggleButton[] targets = {cutButton, bulkButton, mantainButton, toneButton};

        for(ToggleButton t : targets){
            t.setOnAction(e -> {
                for(ToggleButton other : targets){
                    if(other != t){
                        other.setSelected(false);
                    }
                }
            });
        }
    }

    private void configureWorkoutDayButton(){
        ToggleButton[] targets = {twoButton, threeButton, fourButton, fiveButton, sixButton};

        for(ToggleButton t : targets){
            t.setOnAction(e -> {
                for(ToggleButton other : targets){
                    if(other != t){
                        other.setSelected(false);
                    }
                }
            });
        }
    }

    @FXML
    private void onClickWorkoutRequest(){
        errorLabel.setText("");

        boolean missingTextFields = 
            heightField.getText().isEmpty() ||
            weightField.getText().isEmpty() ||
            ageField.getText().isEmpty();
        
        boolean missingGender = 
            maleButton.isSelected() || femaleButton.isSelected();

        boolean missingTarget = 
            cutButton.isSelected() ||
            bulkButton.isSelected() ||
            mantainButton.isSelected() ||
            toneButton.isSelected();

        boolean missingDays = 
            twoButton.isSelected() ||
            threeButton.isSelected() ||
            fourButton.isSelected() ||
            fiveButton.isSelected() ||
            sixButton.isSelected();

        boolean missingCurrentLevel = currLevel.getValue() != null;

        if(!missingGender || missingTextFields || !missingDays || !missingTarget || !missingCurrentLevel){
            errorLabel.setText("Compila tutto il questionario per continuare!");
            return;
        }

        double weight, height;
        int age;

        try{
            weight = Double.parseDouble(weightField.getText());
            height = Double.parseDouble(heightField.getText());
            age = Integer.parseInt(ageField.getText());
        }catch(NumberFormatException e){
            errorLabel.setText("Peso, altezza e età devono essere numeri validi!");
            return;
        }

        String gender = maleButton.isSelected() ? "Maschio" : "Femmina";

        Target target;
        if(cutButton.isSelected()){
            target = Target.Perdere_peso;
        }else if(bulkButton.isSelected()){
            target = Target.Massa_muscolare;
        }else if(mantainButton.isSelected()){
            target = Target.Mantenere;
        }else{
            target = Target.Tonificare;
        }

        WorkoutDay days;
        if(twoButton.isSelected()){
            days = WorkoutDay.Due_volte;
        }else if(threeButton.isSelected()){
            days = WorkoutDay.Tre_volte;
        }else if(fourButton.isSelected()){
            days = WorkoutDay.Quattro_volte;
        }else if(fiveButton.isSelected()){
            days = WorkoutDay.Cinque_volte;
        }else{
            days = WorkoutDay.Sei_volte;
        }

        ActivityLevel level = currLevel.getValue();

    
        currAthlete.setActivityLevel(level);
        currAthlete.setAge(age);
        currAthlete.setGender(gender);
        currAthlete.setHeight(height);
        currAthlete.setWeight(weight);
        currAthlete.setTarget(target);
        currAthlete.setWorkoutDay(days);
        currAthlete.setIsWorkoutRequested(true);

        workoutRequestController.setCurrAthlete(currAthlete);

        onRequestSuccess(currAthlete);
    }

    private void onRequestSuccess(AthleteBean bean){
        errorLabel.setText("Richiesta della scheda effettuata!");
    }

    @FXML
    private void onClickGoBack(){
        Navigator.navigateTo("/fxml/Home.fxml", controller -> {
            ((GuiHomeView) controller).setAthlete(currAthlete);
        });
    }
}
