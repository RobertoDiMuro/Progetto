package it.uniroma2.dicii.EzGym;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button loginButton;
    @FXML private Button btnCreateAccount;
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameField;
    @FXML private Label errorMessageLabel;
    @FXML private TextField passwordTextField;
    @FXML private ImageView passwordVisibilityIcon;

    private boolean showingPassword = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginButton != null) {
            loginButton.setDefaultButton(true);
            loginButton.setOnAction(e -> handleLogin());
        }

        if (btnCreateAccount != null) {
            btnCreateAccount.setOnAction(e -> handleCreateAccount());
        }

        if (passwordTextField != null && passwordField != null) {
            passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
            passwordField.managedProperty().bind(passwordField.visibleProperty());
            passwordTextField.setVisible(false);
            
            passwordTextField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (showingPassword) {
                    passwordField.setText(newVal);
                }
            });
            
            passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!showingPassword) {
                    passwordTextField.setText(newVal);
                }
            });
        }

        if (passwordVisibilityIcon != null) {
            passwordVisibilityIcon.setOnMouseClicked(e -> togglePasswordVisibility());
            passwordVisibilityIcon.setStyle("-fx-cursor: hand;");
        }

        if (usernameField != null) {
            usernameField.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.setOnKeyPressed(ke -> {
                        if (ke.getCode() == KeyCode.ESCAPE) {
                            System.exit(0);
                        }
                    });
                    usernameField.requestFocus();
                }
            });
        }
    }

    private void handleLogin() {
        errorMessageLabel.setText("");
        
        if (!isFieldFilled()) {
            return;
        }
        
        if (isValid()) {
            navigateToHome();
        }
    }

    private void handleCreateAccount() {
        errorMessageLabel.setText("");
        navigateToRegistration();
    }

    private boolean isFieldFilled() {
        StringBuilder sb = new StringBuilder();
        boolean isFilled = true;

        if (usernameField.getText() == null || usernameField.getText().isBlank()) {
            isFilled = false;
            sb.append("Inserisci username o e-mail!");
        }
        
        String pwd = showingPassword ? passwordTextField.getText() : passwordField.getText();
        if (pwd == null || pwd.isBlank()) {
            if (!isFilled) sb.append("\n");
            isFilled = false;
            sb.append("Inserisci password!");
        }
        
        if (!isFilled) {
            errorMessageLabel.setText(sb.toString());
        }
        
        return isFilled;
    }

    private boolean isValid() {
        String username = usernameField.getText();
        String pwd = showingPassword ? passwordTextField.getText() : passwordField.getText();
        
        if (!username.equals(Main.USERNAME) || !pwd.equals(Main.PASSWORD)) {
            errorMessageLabel.setText("Credenziali non valide!");
            return false;
        }
        
        return true;
    }

    private void togglePasswordVisibility() {
        showingPassword = !showingPassword;

        if (showingPassword) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
            passwordTextField.requestFocus();
            passwordTextField.positionCaret(passwordTextField.getText().length());
            setToggleIcon("/Icone/eye.png");
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());
            setToggleIcon("/Icone/hidden.png");
        }
    }

    private void setToggleIcon(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                passwordVisibilityIcon.setImage(new Image(url.toExternalForm()));
            }
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'icona: " + resourcePath);
        }
    }

    private void navigateToHome() {
        Navigator.setRoot("/fxml/Home.fxml");
    }

    private void navigateToRegistration() {
        Navigator.setRoot("/fxml/Registrazione.fxml");
    }
}