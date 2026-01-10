package it.uniroma2.dicii.ezgym.view.guimode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.controller.CreateSessionController;
import it.uniroma2.dicii.ezgym.controller.CreateWorkoutController;
import it.uniroma2.dicii.ezgym.controller.PtRequestcontroller;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GuiCreateWorkoutView {
    
    @FXML private Button addExerciseBtn;

    @FXML private Label errorLabel;

    @FXML private Label dayLabel;

    @FXML private ToggleButton fifthDayBtn;

    @FXML private ToggleButton firstDayBtn;

    @FXML private ToggleButton fourthDayBtn;

    @FXML private Label nameLabel;

    @FXML private TextField repeteWeeksField;

    @FXML private Button saveBtn;

    @FXML private AnchorPane scrollPaneContent;

    @FXML private ToggleButton secondDayBtn;

    @FXML private ToggleButton sixthDayBtn;

    @FXML private ToggleButton thirdDayBtn;

    @FXML private ImageView goBack;

    private PersonalTrainerBean currTrainer;

    private final PtRequestcontroller ptRequestcontroller = new PtRequestcontroller();
    private final CreateWorkoutController createWorkoutController = new CreateWorkoutController();
    private final CreateSessionController createSessionController = new CreateSessionController();

    private final WorkoutSessionBean[] sessions = new WorkoutSessionBean[6];
    @SuppressWarnings("unchecked")
    private final List<Parent>[] dayExerciseViews = new ArrayList[6];

    private AthleteBean currAthlete;

    private VBox sliderBox;
    private int selectedDayIndex = 0;
    private final int[] exerciseCounters = new int[6];
    private static final String[] DAY_LABELS = {
            "Primo giorno",
            "Secondo giorno",
            "Terzo giorno",
            "Quarto giorno",
            "Quinto giorno",
            "Sesto giorno"
    };

    private void resetState() {
        selectedDayIndex = 0;

        for (int i = 0; i < sessions.length; i++) {
            sessions[i] = null;

            if (dayExerciseViews[i] != null) {
                dayExerciseViews[i].clear();
            }
            exerciseCounters[i] = 0;
        }

        if (sliderBox != null) {
            sliderBox.getChildren().clear();
        }

        if (dayLabel != null) {
            dayLabel.setText(DAY_LABELS[0]);
        }

        if (firstDayBtn != null) firstDayBtn.setSelected(true);
        if (secondDayBtn != null) secondDayBtn.setSelected(false);
        if (thirdDayBtn != null) thirdDayBtn.setSelected(false);
        if (fourthDayBtn != null) fourthDayBtn.setSelected(false);
        if (fifthDayBtn != null) fifthDayBtn.setSelected(false);
        if (sixthDayBtn != null) sixthDayBtn.setSelected(false);
    }

    public void init(PersonalTrainerBean personalTrainer, AthleteBean athlete){
        this.currTrainer = personalTrainer;
        this.currAthlete = athlete;

        resetState();

        if (nameLabel != null && currAthlete != null) {
            nameLabel.setText(currAthlete.getName() + " " + currAthlete.getSurname());
        }
        onDaySelected(0);  
    }

   @FXML
   private void initialize(){

        configureDayButton();

        sliderBox = new VBox();
        sliderBox.setFillWidth(true);

        scrollPaneContent.getChildren().setAll(sliderBox);
        AnchorPane.setTopAnchor(sliderBox, 0.0);
        AnchorPane.setRightAnchor(sliderBox, 0.0);
        AnchorPane.setLeftAnchor(sliderBox, 0.0);

        for (int i = 0; i < dayExerciseViews.length; i++) {
            dayExerciseViews[i] = new ArrayList<>();
            exerciseCounters[i] = 0;
        }

        firstDayBtn.setSelected(true);
        onDaySelected(0);
   }

   

   private void configureDayButton(){
        ToggleButton[] days = {firstDayBtn, secondDayBtn, thirdDayBtn, fourthDayBtn, fifthDayBtn, sixthDayBtn};

        for (int i = 0; i < days.length; i++) {
            final int index = i;

            if (days[index] == null) continue;

            days[index].setOnAction(e -> onDaySelected(index));
        }
    }


   private void onDaySelected(int index) {
        selectedDayIndex = index;

        ToggleButton[] days = {firstDayBtn, secondDayBtn, thirdDayBtn, fourthDayBtn, fifthDayBtn, sixthDayBtn};

        for (int j = 0; j < days.length; j++) {
            if (days[j] != null && j != index) {
                days[j].setSelected(false);
            }
        }

        if (dayLabel != null) {
            dayLabel.setText(DAY_LABELS[index]);
        }

        ensureSessionCreated(index);

        sliderBox.getChildren().setAll(dayExerciseViews[index]);
    }


    private void ensureSessionCreated(int index) {
        if (sessions[index] != null) return;

        if (currAthlete == null) return;

        WorkoutSessionBean bean = new WorkoutSessionBean();
        bean.setDayOfWeek(DAY_LABELS[index]);

        createSessionController.createEmptySession(bean);

        sessions[index] = bean;
    }

    @FXML
    private void onClickAddExercise() {

        ensureSessionCreated(selectedDayIndex);

        if (sessions[selectedDayIndex] == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AggiungiEsercizio.fxml"));
            Parent root = loader.load();

            GuiAddExerciseView addExerciseView = loader.getController();
            addExerciseView.init(sessions[selectedDayIndex].getSessionId());

            exerciseCounters[selectedDayIndex]++;
            addExerciseView.setExerciseIdex(exerciseCounters[selectedDayIndex]);

            dayExerciseViews[selectedDayIndex].add(root);
            sliderBox.getChildren().add(root);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onClickSaveWorkout() {
        errorLabel.setText("");

        if (currAthlete == null) {
            return;
        }

        String weeks = repeteWeeksField.getText();
        if (weeks == null || weeks.trim().isEmpty()) {
            errorLabel.setText("Compila tutti i campi!");
            return;
        }

        int repeteWeeks;
        try {
            repeteWeeks = Integer.parseInt(weeks.trim());
        } catch (NumberFormatException e) {
            errorLabel.setText("Le settimane inserite non sono un numero valido!");
            return;
        }

        WorkoutBean workoutBean = new WorkoutBean();

        workoutBean.setAthleteId(currAthlete.getId());
        workoutBean.setRepeteWeeks(repeteWeeks);

        List<WorkoutSessionBean> sessionBeans = new ArrayList<>();
        for (WorkoutSessionBean s : sessions) {
            if (s != null) {
                if (s.getSessionId() <= 0) {
                    repeteWeeksField.setText("");
                    repeteWeeksField.setPromptText("Errore: sessione non inizializzata");
                    return;
                }
                sessionBeans.add(s);
            }
        }

        if (sessionBeans.isEmpty()) {
            repeteWeeksField.setText("");
            repeteWeeksField.setPromptText("Aggiungi almeno una sessione");
            return;
        }

        workoutBean.setSessions(sessionBeans);
        createWorkoutController.saveWorkout(workoutBean);

        onSaveSuccess();
    }

    @FXML
    private void goBack(){
        Navigator.navigateTo("/fxml/Homept.fxml", controller -> {
            ((GuiHomeptView) controller).setPersonalTrainer(currTrainer);
        });
    }

    private void onSaveSuccess(){
        errorLabel.setText("Scheda creata con successo!");

        ptRequestcontroller.closeRequest(currAthlete.getId()); 

        Navigator.navigateTo("/fxml/Homept.fxml", controller -> {
            ((GuiHomeptView) controller).setPersonalTrainer(currTrainer);
        });
    }

}
