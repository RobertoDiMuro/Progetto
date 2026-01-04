package it.uniroma2.dicii.ezgym.view;

import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.User;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiLoginView {

    @FXML private Button btnCreateAccount;
    @FXML private Label errorMessageLabel;
    @FXML private Button loginButton;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private ImageView passwordVisibilityIcon;
    @FXML private TextField emailField;

    private final LoginController loginController = new LoginController();
    private boolean isPasswordVisible = false;

    public GuiLoginView() {
        // costruttore vuoto richiesto da JavaFX
    }

    @FXML
    void initialize() {
        // textfield “normale” nascosto all’inizio
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        // quando uno è visibile, l’altro non lo è
        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());

        // tieni sincronizzati i testi
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        updateVisibilityIcon();
    }

    @FXML
    private void onClikSetVisibility() {
        setPasswordVisible(!isPasswordVisible);
    }

    private void setPasswordVisible(boolean visible) {
        this.isPasswordVisible = visible;

        passwordTextField.setVisible(visible);
        passwordField.setVisible(!visible);

        updateVisibilityIcon();

        if (visible) {
            passwordTextField.requestFocus();
            passwordTextField.positionCaret(passwordTextField.getText().length());
        } else {
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());
        }
    }

    private void updateVisibilityIcon() {
        String iconName = isPasswordVisible ? "eye.png" : "hidden.png";
        String iconPath = "/icone/" + iconName;

        var url = getClass().getResource(iconPath);
        if (url == null) {
            System.err.println("Immagine non trovata: " + iconPath);
            return;
        }

        Image image = new Image(url.toExternalForm());
        passwordVisibilityIcon.setImage(image);
    }

    @FXML
    private void onClickLogin() {
        errorMessageLabel.setText("");

        String email = emailField.getText();
        String password = isPasswordVisible
                ? passwordTextField.getText()
                : passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Email e password sono obbligatori.");
            return;
        }

        try {
            UserBean userBean = new UserBean();
            userBean.setEmail(email);
            userBean.setPassword(password);

            User genericUser = loginController.login(userBean);

            if (genericUser instanceof Athlete authenticatedAthlete) {

                Navigator.navigateTo("/fxml/Home.fxml", controller -> {
                    ((GuiHomeView) controller).setAthlete(authenticatedAthlete);
                });

            } 
            if (genericUser instanceof PersonalTrainer trainer) {
                Navigator.navigateTo("/fxml/Homept.fxml", controller -> {
                    ((GuiHomeptView) controller).setPersonalTrainer(trainer);
                }); 
            } 

        } catch (InvalidCredentialsException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorMessageLabel.setText("Errore inatteso.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickCreateAccount() {
        Navigator.navigateTo("/fxml/Signup.fxml");
    }
}
