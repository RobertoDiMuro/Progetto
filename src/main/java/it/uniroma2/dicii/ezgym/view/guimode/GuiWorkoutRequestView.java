package it.uniroma2.dicii.ezgym.view.guimode;


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

        double weight;
        double height;
        int age;

        try{
            weight = Double.parseDouble(weightField.getText());
            height = Double.parseDouble(heightField.getText());
            age = Integer.parseInt(ageField.getText());
        }catch(NumberFormatException _){
            errorLabel.setText("Peso, altezza e età devono essere numeri validi!");
            return;
        }

        String gender = maleButton.isSelected() ? "Maschio" : "Femmina";

        Target target = resolveTarget();
        WorkoutDay days = resolveWorkoutDay();

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

        onRequestSuccess();
    }


    private Target resolveTarget(){
        if(cutButton.isSelected()){
            return Target.PERDERE_PESO;
        }
        if(bulkButton.isSelected()){
            return Target.MANTENERE;
        }
        if(mantainButton.isSelected()){
            return Target.MANTENERE;
        }
        return Target.TONIFICARE;
    }

    private WorkoutDay resolveWorkoutDay(){
        if(twoButton.isSelected()){
            return WorkoutDay.DUE_VOLTE;
        }
        if(threeButton.isSelected()){
            return WorkoutDay.TRE_VOLTE;
        }
        if(fourButton.isSelected()){
            return WorkoutDay.QUATTRO_VOLTE;
        }
        if(fiveButton.isSelected()){
            return WorkoutDay.CINQUE_VOLTE;
        }
        return WorkoutDay.SEI_VOLTE;
    }

    private void onRequestSuccess(){
        errorLabel.setText("Richiesta della scheda effettuata!");
    }

    @FXML
    private void onClickGoBack(){
        Navigator.navigateTo("/fxml/Home.fxml", controller -> {
            ((GuiHomeView) controller).setAthlete(currAthlete);
        });
    }
}
