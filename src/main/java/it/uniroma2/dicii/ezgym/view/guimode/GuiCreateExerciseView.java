package it.uniroma2.dicii.ezgym.view.guimode;

import java.util.List;

import it.uniroma2.dicii.ezgym.bean.ExerciseBean;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.controller.ExerciseController;
import it.uniroma2.dicii.ezgym.exceptions.PersistenceException;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GuiCreateExerciseView {
    
    @FXML private TableView<ExerciseBean> exerciseTable;
    @FXML private TableColumn<ExerciseBean, String> nameColumn;
    @FXML private TableColumn<ExerciseBean, String> focusColumn;

    @FXML private TextField focusField;
    @FXML private ImageView goBack;
    @FXML private TextField nameField;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;

    private final ObservableList<ExerciseBean> exercises = FXCollections.observableArrayList();

    private PersonalTrainerBean personalTrainer;

    private final ExerciseController exerciseController = new ExerciseController();

    public void setPersonalTrainer(PersonalTrainerBean pt){
        this.personalTrainer = pt;
    }

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        focusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFocus()));

        exerciseTable.setItems(exercises);
        saveButton.setOnAction(e -> onSave());
        goBack.setPickOnBounds(true);
        goBack.setOnMouseClicked(e -> Navigator.navigateTo("/fxml/Homept.fxml"));

        reload();
    }

    private void reload() {
        try {
            List<ExerciseBean> list = exerciseController.getAllExercises();
            exercises.setAll(list);
        } catch (PersistenceException _) {
            //
        }
    }

    private void onSave(){
        errorLabel.setText("");

        String name = nameField.getText();
        String focus = focusField.getText();

        if(name.isEmpty() || focus.isEmpty()){
            errorLabel.setText("Compila tutti i campi!");
            return;
        }

        ExerciseBean exerciseBean = new ExerciseBean();
        exerciseBean.setName(name.trim());
        exerciseBean.setFocus(focus.trim());

        try {
            exerciseController.createExercise(exerciseBean);
            onSaveSuccess();
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void onSaveSuccess(){
        nameField.clear();
        focusField.clear();
        reload();
    }

    @FXML
    private void goBack(){
        Navigator.navigateTo("/fxml/Homept.fxml", controller -> {
                    ((GuiHomeptView) controller).setPersonalTrainer(personalTrainer);
        });
    }

}
