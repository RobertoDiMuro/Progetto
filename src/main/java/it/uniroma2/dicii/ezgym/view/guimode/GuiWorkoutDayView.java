package it.uniroma2.dicii.ezgym.view.guimode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma2.dicii.ezgym.bean.SessionExerciseBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.domain.model.Exercise;
import it.uniroma2.dicii.ezgym.domain.model.ExerciseType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiWorkoutDayView {
    
    @FXML private TitledPane dayOfWeek;

    @FXML private TableColumn<SessionExerciseBean, String> exerciseColumn;

    @FXML private TableColumn<SessionExerciseBean, String> focusColumn;

    @FXML private TableColumn<SessionExerciseBean, String> noteColumn;

    @FXML private TableColumn<SessionExerciseBean, Integer> repsColumn;

    @FXML private TableColumn<SessionExerciseBean, Integer> setsColumn;

    @FXML private TableColumn<SessionExerciseBean, Double> timeColumn;

    @FXML private TableColumn<SessionExerciseBean, ExerciseType> typeColumn;

    @FXML private TableView<SessionExerciseBean> workoutTable;

    private final Map<String, String> focusCache = new HashMap<>();

    @FXML
    public void initialize() {

        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseName"));
        setsColumn.setCellValueFactory(new PropertyValueFactory<>("sets"));
        repsColumn.setCellValueFactory(new PropertyValueFactory<>("reps"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("restTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        focusColumn.setCellValueFactory(cell -> {
            SessionExerciseBean se = cell.getValue();
            String name = (se != null) ? se.getExerciseName() : null;
            String focus = focusCache.getOrDefault(name, "");
            return new SimpleStringProperty(focus);
        });
    }

    public void setSession(WorkoutSessionBean session) {
        if (session == null) {
            workoutTable.setItems(FXCollections.observableArrayList());
            return;
        }

        if (dayOfWeek != null) {
            dayOfWeek.setText(session.getDayOfWeek());
        }

        List<SessionExerciseBean> exercises = session.getExercises();
        if (exercises == null) exercises = new ArrayList<>();

        focusCache.clear();
        for (SessionExerciseBean se : exercises) {
            String name = (se != null) ? se.getExerciseName() : null;
            if (name != null && !name.isBlank() && !focusCache.containsKey(name)) {
                focusCache.put(name, loadFocus(name));
            }
        }

        List<SessionExerciseBean> sorted = new ArrayList<>(exercises);
        sorted.sort(Comparator.comparing(
                se -> focusCache.getOrDefault(se.getExerciseName(), ""),
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        ));

        workoutTable.setItems(FXCollections.observableArrayList(sorted));
    }

    private String loadFocus(String exerciseName) {
        try {
            Exercise ex = DaoFactory.getInstance().createExerciseDao().findBy(exerciseName);
            if (ex != null && ex.getFocus() != null) return ex.getFocus();
        } catch (RuntimeException _) { 
            //
        }
        return "";
    }

}
