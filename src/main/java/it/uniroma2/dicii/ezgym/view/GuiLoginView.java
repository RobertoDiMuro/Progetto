package it.uniroma2.dicii.ezgym.view;



import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.exceptions.InvalidCredentialsException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;


public class GuiLoginView {

    @FXML private Button btnCreateAccount;

    @FXML private Label errorMessageLabel;

    @FXML private Button loginButton;

    @FXML private PasswordField passwordField;

    @FXML private TextField passwordTextField;

    @FXML private ImageView passwordVisibilityIcon;

    @FXML private TextField emailField;

    private final LoginController loginController = new LoginController();
    boolean isPasswordVisible = false;

    public GuiLoginView(){
        //
    }

   @FXML 
    void initialize(){
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());

        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        updateVisibilityIcon();
    }


    @FXML 
    private void onClikSetVisibility(){
        setPasswordVisible(!isPasswordVisible);
    }

    private void setPasswordVisible(boolean visible){
        this.isPasswordVisible = visible;

        passwordTextField.setVisible(visible);
        passwordField.setVisible(!visible);

        updateVisibilityIcon();

        if(visible){
            passwordTextField.requestFocus();
            passwordTextField.positionCaret(passwordTextField.getText().length());
        }else{
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());
        }
    }

    private void updateVisibilityIcon(){
        String iconName = isPasswordVisible ? "eye.png" : "hidden.png";
        String iconPath = "/icone/" + iconName;
        var url = getClass().getResource(iconPath);

        if(url == null){
            System.err.println("Immagine non trovata");
        }

        Image image = new Image(url.toExternalForm());
        passwordVisibilityIcon.setImage(image);
    }

    @FXML
private void onClickLogin() {
    errorMessageLabel.setText("");

    String email = emailField.getText();
    String password = isPasswordVisible ?
            passwordTextField.getText() :
            passwordField.getText();

    if (email.isEmpty() || password.isEmpty()) {
        errorMessageLabel.setText("Email e password sono obbligatori.");
        return;
    }

    try {
        UserBean user = new UserBean();
        user.setEmail(email);
        user.setPassword(password);

        loginController.login(user);

        onLoginSuccess();

    } catch (InvalidCredentialsException e) {
        errorMessageLabel.setText(e.getMessage());
    } catch (IllegalArgumentException e) {
        errorMessageLabel.setText(e.getMessage());
    } catch (Exception e) {
        errorMessageLabel.setText("Errore inatteso.");
        e.printStackTrace();
    }
}


    public void onLoginSuccess(){
        errorMessageLabel.setText("Login effettuato con successo!");
    }



}
