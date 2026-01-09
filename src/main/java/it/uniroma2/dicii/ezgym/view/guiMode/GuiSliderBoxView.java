package it.uniroma2.dicii.ezgym.view.guiMode;

import java.io.IOException;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GuiSliderBoxView {

    @FXML private Label bmiLabel;

    @FXML private Button createDietButton;

    @FXML private Button createWorkoutButton;

    @FXML private Label goalLabel;

    @FXML private Label nameLabel;

    @FXML private Label requestLabel;

    @FXML private Button reviewButton;

    @FXML private Label timeLabel;

    @FXML private Label weightLabel;

    @FXML private Label errorLabel;

    private AthleteBean athlete;
    private PersonalTrainerBean trainer;

    public void setPersonalTrainer(PersonalTrainerBean trainer){
         this.trainer = trainer; 
    }

    public void setAthleteBean(AthleteBean bean){
        this.athlete = bean;

        nameLabel.setText(athlete.getName() + " " + athlete.getSurname());
        weightLabel.setText("Peso: " + athlete.getWeight() + " kg |");
        goalLabel.setText("Obiettivo: " + athlete.getTarget());

        if(athlete.getWeight() > 0 && athlete.getHeight() > 0){
            double heightInMeters = athlete.getHeight()/100.0;
            double bmi = athlete.getWeight() / (heightInMeters * heightInMeters);
            bmi = Math.round(bmi * 100.0) / 100.0;

            bmiLabel.setText("BMI: " + bmi + " |");
        }else{
            bmiLabel.setText("BMI: N/A |");
        }

        timeLabel.setText("Richiesta di recente");
        requestLabel.setText("Scheda");
    }
    
    @FXML
    private void onReviewButtonClick() {
        if (athlete == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/VisualizzaDati.fxml"));
            Parent root = loader.load();

            GuiReviewDataView controller = loader.getController();
            controller.setAthleteBean(athlete);

            Stage stage = new Stage();
            stage.setTitle("Dati atleta");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(reviewButton.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickCreateWorkout() {
        Navigator.navigateTo("/fxml/CreateWorkout.fxml", controller -> {
            GuiCreateWorkoutView v = (GuiCreateWorkoutView) controller;
            v.init(trainer, athlete); 
        });
    }

    @FXML
    private void onClickCreateDiet(){
        errorLabel.setText("Funzionalità non ancora implementata!");
    }
}
