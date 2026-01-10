package it.uniroma2.dicii.ezgym.view.guiMode;



import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.controller.AddExerciseToSessionController;
import it.uniroma2.dicii.ezgym.dao.dbms.ExerciseDbmsDao;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class GuiAddExerciseView {

    @FXML private Button addButton;

    @FXML private Label errorLabel;

    @FXML private ComboBox<String> exerciseChooseBox;

    @FXML private TitledPane exerciseNumber;

    @FXML private TextField notesField;

    @FXML private TextField repsField;

    @FXML private TextField restTimeField;

    @FXML private TextField setsField;

    @FXML private ComboBox<ExerciseType> typeBox;

    private int sessionId;
    private final AddExerciseToSessionController controller = new AddExerciseToSessionController();
    private final ExerciseDao exerciseDao = ExerciseDbmsDao.getInstance();

    public void init(int session_Id){
        this.sessionId = session_Id;
    }

    @FXML
    private void initialize(){
        errorLabel.setText("");

        typeBox.getItems().setAll(ExerciseType.values());

        var exercises = exerciseDao.findAll();
        exerciseChooseBox.setItems(FXCollections.observableArrayList(
            exercises.stream()
                     .map(Exercise::getName)
                     .toList()));
        
        addButton.setOnAction(e -> onClickAdd());
        
    }

    public void setExerciseIdex(int index){
        if(exerciseNumber != null){
            exerciseNumber.setText("Esercizio " + index);
        }
    }

    @FXML
    private void onClickAdd(){
        errorLabel.setText("");

        boolean missingExercise = exerciseChooseBox.getValue() != null;
        
        boolean missingReps = repsField.getText().isEmpty();
        boolean missingSets = setsField.getText().isEmpty();
        boolean missingRestTime = restTimeField.getText().isEmpty();

        boolean missingType = typeBox.getValue() != null;

        if(!missingExercise || missingReps || missingSets || missingRestTime || !missingType){
            errorLabel.setText("Compila tutti i campi!");
            return;
        }

        int reps, sets;
        double restTime;
        try{
            reps = Integer.parseInt(repsField.getText());
            sets = Integer.parseInt(setsField.getText());
            restTime = Double.parseDouble(restTimeField.getText());
        }catch(NumberFormatException e){
            errorLabel.setText("Inserisci valori numerici validi per serie, ripetizioni e tempo di recupero!");
            return;
        }

        String exerciseName = exerciseChooseBox.getValue();
        String notes = notesField.getText();
        ExerciseType type = typeBox.getValue();

        SessionExerciseBean bean = new SessionExerciseBean();
        bean.setSessionId(sessionId);
        bean.setExerciseName(exerciseName);
        bean.setReps(reps);
        bean.setSets(sets);
        bean.setRestTime(restTime);
        bean.setType(type);
        bean.setNotes(notes);

        controller.addExerciseToSession(bean);

        errorLabel.setText("Esercizio aggiunto con successo!");
    }
}

