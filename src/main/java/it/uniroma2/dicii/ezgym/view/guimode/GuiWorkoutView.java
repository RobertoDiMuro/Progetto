package it.uniroma2.dicii.ezgym.view.guimode;

import java.io.IOException;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutBean;
import it.uniroma2.dicii.ezgym.bean.WorkoutSessionBean;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiWorkoutView {

    @FXML private ImageView goBack;

    @FXML private Label nameLabel;

    @FXML private Label repeteWeeksLabel;

    @FXML private Button requestButton;

    @FXML private ScrollPane workoutScrollPane;

    private AthleteBean athlete;

    public void setAthlete(AthleteBean currAthlete){
        this.athlete = currAthlete;
        nameLabel.setText(athlete.getName() + " " + athlete.getSurname());
    }

    public void setWorkout(WorkoutBean workout){
        if (workout != null) {
            repeteWeeksLabel.setText("Ripeti per: " + workout.getRepeteWeeks() + " settimane");
        }

        VBox container = new VBox();
        container.setSpacing(10);

        if (workout != null) {
            List<WorkoutSessionBean> sessions = workout.getsSessions();
            if (sessions != null) {
                for (WorkoutSessionBean s : sessions) {
                    Node dayNode = loadDayView(s);
                    if (dayNode != null) {
                        container.getChildren().add(dayNode);
                    }
                }
            }
        }

        workoutScrollPane.setContent(container);
        workoutScrollPane.setFitToWidth(true);
    }

    private Node loadDayView(WorkoutSessionBean session) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GiorniScheda.fxml"));
            Node root = loader.load();

            GuiWorkoutDayView controller = loader.getController();
            controller.setSession(session);

            return root;
        } catch (IOException e) {
            return null;
        }
    }

    @FXML
    private void goBack(){
        Navigator.navigateTo("/fxml/Home.fxml", controller -> {
            ((GuiHomeView) controller).setAthlete(athlete);
        });
    }

    @FXML
    private void onClickWorkoutRequest() {
        Navigator.navigateTo("/fxml/WorkoutRequest.fxml", controller -> {
            ((GuiWorkoutRequestView) controller).setAthlete(athlete);
        });
    }  
}
