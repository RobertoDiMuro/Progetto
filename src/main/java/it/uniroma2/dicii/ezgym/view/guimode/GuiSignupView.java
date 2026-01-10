package it.uniroma2.dicii.ezgym.view.guimode;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.SignupBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.SignupController;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.Role;
import it.uniroma2.dicii.ezgym.domain.model.User;
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

    public GuiSignupView() {}

    @FXML
    void initialize() {
        setupPasswordField(pfPasswordField, tfPasswordField);
        setupPasswordField(pfConfirmField, tfConfirmfield);

        updateVisibilityIcon(toggleEye1, isPwVisible1);
        updateVisibilityIcon(toggleeye2, isPwVisible2);
    }

    private void setupPasswordField(PasswordField pf, TextField tf) {
        tf.setVisible(false);
        tf.setManaged(false);

        tf.managedProperty().bind(tf.visibleProperty());
        pf.managedProperty().bind(pf.visibleProperty());

        tf.textProperty().bindBidirectional(pf.textProperty());
    }

    @FXML
    private void onClickSetToggleEye1() {
        isPwVisible1 = !isPwVisible1;
        toggleVisibility(isPwVisible1, pfPasswordField, tfPasswordField);
        updateVisibilityIcon(toggleEye1, isPwVisible1);
    }

    @FXML
    private void onClickSetToggleEye2() {
        isPwVisible2 = !isPwVisible2;
        toggleVisibility(isPwVisible2, pfConfirmField, tfConfirmfield);
        updateVisibilityIcon(toggleeye2, isPwVisible2);
    }

    private void toggleVisibility(boolean isVisible, PasswordField pf, TextField tf) {
        tf.setVisible(isVisible);
        pf.setVisible(!isVisible);

        if (isVisible) {
            tf.requestFocus();
            tf.positionCaret(tf.getText().length());
        } else {
            pf.requestFocus();
            pf.positionCaret(pf.getText().length());
        }
    }

    private void updateVisibilityIcon(ImageView icon, boolean isVisible) {
        String iconName = isVisible ? "eye.png" : "hidden.png";
        String iconPath = "/icone/" + iconName;

        var url = getClass().getResource(iconPath);
        if (url == null) {
            System.err.println("Immagine non trovata: " + iconName);
            return;
        }

        Image image = new Image(url.toExternalForm());
        icon.setImage(image);
    }

    @FXML
    private void onClickSignup() {
        errorMessageLabel.setText("");

        try {
            SignupBean bean = new SignupBean();

            bean.setName(nameField.getText());
            bean.setSurname(surnameField.getText());
            bean.setEmail(emailFied.getText());
            bean.setPassword(isPwVisible1 ? tfPasswordField.getText() : pfPasswordField.getText());
            bean.setConfirmPw(
                    isPwVisible2 ? tfConfirmfield.getText() : pfConfirmField.getText(),
                    isPwVisible1 ? tfPasswordField.getText() : pfPasswordField.getText()
            );

            UserBean createdBase = signupController.signup(bean);
            String email = createdBase.getEmail().trim().toLowerCase();

            AthleteBean currAthlete = loadAthleteBeanForHome(email);

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

    private AthleteBean loadAthleteBeanForHome(String normalizedEmail) {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao userDao = factory.createUserDao();
        AthleteDao athleteDao = factory.createAthleteDao();

        User user = userDao.findByEmail(normalizedEmail);
        Athlete athlete = athleteDao.findBy(normalizedEmail);

        AthleteBean bean = new AthleteBean();

        if (user != null) {
            bean.setId(user.getId());
            bean.setName(user.getName());
            bean.setSurname(user.getSurname());
            bean.setEmail(normalizedEmail);
            bean.setRole(Role.ATHLETE);
        } else {
            bean.setEmail(normalizedEmail);
            bean.setRole(Role.ATHLETE);
            bean.setName("Utente");
            bean.setSurname("");
        }

        if (athlete != null) {
            if (athlete.getGender() != null && !athlete.getGender().trim().isEmpty()) {
                bean.setGender(athlete.getGender());
            }
            if (athlete.getAge() > 0) {
                bean.setAge(athlete.getAge());
            }
            if (athlete.getWeight() > 0) {
                bean.setWeight(athlete.getWeight());
            }
            if (athlete.getHeight() > 0) {
                bean.setHeight(athlete.getHeight());
            }
            if (athlete.getTarget() != null) {
                bean.setTarget(athlete.getTarget());
            }
            if (athlete.getActivityLevel() != null) {
                bean.setActivityLevel(athlete.getActivityLevel());
            }
            if (athlete.getWorkoutDay() != null) {
                bean.setWorkoutDay(athlete.getWorkoutDay());
            }
            bean.setIsWorkoutRequested(athlete.getIsWorkoutRequested());
        }

        return bean;
    }

    private void onSignupSuccess(AthleteBean currAthlete) {
        Navigator.navigateTo("/fxml/Home.fxml", controller -> {
            ((GuiHomeView) controller).setAthlete(currAthlete);
        });
    }

    @FXML
    private void onClickGoToLogin() {
        Navigator.navigateTo("/fxml/Login.fxml");
    }
}
