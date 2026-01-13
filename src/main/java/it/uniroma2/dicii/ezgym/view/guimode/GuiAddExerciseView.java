package it.uniroma2.dicii.ezgym.view.guimode;

import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.controller.AddExerciseToSessionController;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.ExerciseDao;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.StringConverter;

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
    private final ExerciseDao exerciseDao = DaoFactory.getInstance().createExerciseDao();

    public void init(int id){
        this.sessionId = id;
    }

    @FXML
    private void initialize(){
        errorLabel.setText("");

        typeBox.getItems().setAll(ExerciseType.values());

        typeBox.setConverter(new StringConverter<ExerciseType>() {
            @Override
            public String toString(ExerciseType type) {
                return type == null ? "" : type.getLabel();
            }

            @Override
            public ExerciseType fromString(String string) {
                return ExerciseType.fromString(string);
            }
        });

        typeBox.setCellFactory(cb -> new ListCell<ExerciseType>() {
            @Override
            protected void updateItem(ExerciseType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLabel());
            }
        });

        typeBox.setButtonCell(new ListCell<ExerciseType>() {
            @Override
            protected void updateItem(ExerciseType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getLabel());
            }
        });

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

        String exerciseName = exerciseChooseBox.getValue();

        int reps = Integer.parseInt(repsField.getText());
        int sets = Integer.parseInt(setsField.getText());
        int restTime = Integer.parseInt(restTimeField.getText());
        ExerciseType type = typeBox.getValue();

        String notes = notesField.getText();

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
