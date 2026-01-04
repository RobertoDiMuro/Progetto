package it.uniroma2.dicii.ezgym.view;


import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.controller.SignupController;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.exceptions.EmailAlreadyExistsException;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GuiSignupView {

    @FXML private ImageView goToLogin;
    
    @FXML private TextField emailFied;

    @FXML private Label errorMessageLabel;

    @FXML private TextField nameField;

    @FXML private PasswordField pfConfirmField;

    @FXML private PasswordField pfPasswordField;

    @FXML private Button signupButton;

    @FXML private TextField surnameField;

    @FXML private TextField tfConfirmfield;
    
    @FXML private TextField tfPasswordField;

    @FXML private ImageView toggleEye1;

    @FXML private ImageView toggleeye2;

    private final SignupController signupController = new SignupController();
    boolean isPwVisible1 = false;
    boolean isPwVisible2 = false;

    public GuiSignupView(){
        //
    }

    @FXML
    void initialize(){
        setupPasswordField(pfPasswordField, tfPasswordField);
        setupPasswordField(pfConfirmField, tfConfirmfield);

        updateVisibilityIcon(toggleEye1, isPwVisible1);
        updateVisibilityIcon(toggleeye2, isPwVisible2);
    }

    private void setupPasswordField(PasswordField pf, TextField tf){
        tf.setVisible(false);
        tf.setManaged(false);

        tf.managedProperty().bind(tf.visibleProperty());
        pf.managedProperty().bind(pf.visibleProperty());

        tf.textProperty().bindBidirectional(pf.textProperty());
    }

    @FXML
    private void onClickSetToggleEye1(){
        isPwVisible1 = !isPwVisible1;
        toggleVisibility(isPwVisible1, pfPasswordField, tfPasswordField);
        updateVisibilityIcon(toggleEye1, isPwVisible1);
    }

    @FXML
    private void onClickSetToggleEye2(){
        isPwVisible2 = !isPwVisible2;
        toggleVisibility(isPwVisible2, pfConfirmField, tfConfirmfield);
        updateVisibilityIcon(toggleeye2, isPwVisible2);
    }

    private void toggleVisibility(boolean isVisible, PasswordField pf, TextField tf){
        tf.setVisible(isVisible);
        pf.setVisible(!isVisible);

        if(isVisible){
            tf.requestFocus();
            tf.positionCaret(tf.getText().length());
        }else{
            pf.requestFocus();
            pf.positionCaret(pf.getText().length());
        }
    }

    private void updateVisibilityIcon(ImageView icon, boolean isVisible){
        String iconName = isVisible ? "eye.png" : "hidden.png";
        String iconPath = "/icone/" + iconName;

        var url = getClass().getResource(iconPath);
        if(url == null){
            System.err.println("Immagine non trovata: " + iconName);
            return;
        }

        Image image = new Image(url.toExternalForm());
        icon.setImage(image);
    }

    @FXML
    private void onClickSignup(){
        errorMessageLabel.setText("");

        try{
            SignupBean bean =new SignupBean();

            bean.setName(nameField.getText());
            bean.setSurname(surnameField.getText());
            bean.setEmail(emailFied.getText());
            bean.setPassword(isPwVisible1 ? tfPasswordField.getText() : pfPasswordField.getText());
            bean.setConfirmPw(isPwVisible2 ? tfConfirmfield.getText() : pfConfirmField.getText(), isPwVisible1 ? tfPasswordField.getText() : pfPasswordField.getText());

            Athlete currAthlete = signupController.signup(bean);
            
            onSignupSuccess(currAthlete);

        } catch (EmailAlreadyExistsException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorMessageLabel.setText("Errore inatteso.");
            e.printStackTrace();
        }
    }
    
    private void onSignupSuccess(Athlete currAthlete){
        Navigator.navigateTo("/fxml/Home.fxml", controller -> {
            ((GuiHomeView) controller).setAthlete(currAthlete);
        });
    }

    @FXML
    private void onClickGoToLogin(){
        Navigator.navigateTo("/fxml/Login.fxml");
    }

}
