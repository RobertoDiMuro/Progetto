package it.uniroma2.dicii.ezgym.view.guimode;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GuiReviewDataView {

    @FXML private Label activityLevelLabel;

    @FXML private Label ageLabel;

    @FXML private Label bmiLabel;

    @FXML private Label emailLabel;

    @FXML private Label genderLabel;

    @FXML private Label goalLabel;

    @FXML private Label heightLabel;

    @FXML private Label nameLabel;

    @FXML private Label surnameLabel;

    @FXML private Label weightLabel;

    @FXML private Label workoutDayLabel;
    
    private AthleteBean athlete;

    public void setAthleteBean(AthleteBean bean){
        this.athlete = bean;
        populateFields();
    }

    private void populateFields(){
        if(athlete == null){
            return;
        }

        nameLabel.setText("Nome: " + athlete.getName());
        surnameLabel.setText("Cognome: " + athlete.getSurname());
        emailLabel.setText("Email: " + athlete.getEmail());
        genderLabel.setText("Genere: " + athlete.getGender());
        ageLabel.setText("Età: " + athlete.getAge() + " anni");
        weightLabel.setText("Peso: " + athlete.getWeight() + " kg");
        heightLabel.setText("Altezza: " + athlete.getHeight() + " cm");
        goalLabel.setText("Obiettivo: " + athlete.getTarget());
        activityLevelLabel.setText("Livello di attività: " + athlete.getActivityLevel());
        workoutDayLabel.setText("Giorni di allenamento: " + athlete.getWorkoutDay());

        double heightInMeters = athlete.getHeight()/100.0;
        double bmi = athlete.getWeight() / (heightInMeters * heightInMeters);
        bmi = Math.round(bmi * 100.0) / 100.0;
        bmiLabel.setText("BMI: " + bmi);
    }
}
