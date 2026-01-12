package it.uniroma2.dicii.ezgym.view.guimode;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.bean.UserBean;
import it.uniroma2.dicii.ezgym.controller.LoginController;
import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfacedao.AthleteDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.PersonalTrainerDao;
import it.uniroma2.dicii.ezgym.dao.interfacedao.UserDao;
import it.uniroma2.dicii.ezgym.domain.model.Athlete;
import it.uniroma2.dicii.ezgym.domain.model.PersonalTrainer;
import it.uniroma2.dicii.ezgym.domain.model.Role;
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
        //
    }

    @FXML
    void initialize() {
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);

        passwordTextField.managedProperty().bind(passwordTextField.visibleProperty());
        passwordField.managedProperty().bind(passwordField.visibleProperty());

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
        String password = isPasswordVisible ? passwordTextField.getText() : passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Email e password sono obbligatori.");
            return;
        }

        try {
            UserBean credentials = new UserBean();
            credentials.setEmail(email);
            credentials.setPassword(password);

            UserBean loggedBase = loginController.login(credentials);
            String normalizedEmail = loggedBase.getEmail();

            DaoFactory factory = DaoFactory.getInstance();
            UserDao userDao = factory.createUserDao();
            AthleteDao athleteDao = factory.createAthleteDao();
            PersonalTrainerDao ptDao = factory.createPersonalTrainerDao();

            User user = userDao.findByEmail(normalizedEmail);
            if (user == null) {
                errorMessageLabel.setText("Utente non trovato.");
                return;
            }

            Role role = user.getRole();
            if (role == null) {
                role = loggedBase.getRole();
            }
            if (role == null) {
                errorMessageLabel.setText("Ruolo utente non valido.");
                return;
            }
            switch (role) {
                case ATHLETE -> {
                    Athlete athlete = athleteDao.findBy(normalizedEmail); 
                    AthleteBean athleteBean = buildAthleteBean(user, athlete, normalizedEmail);

                    Navigator.navigateTo("/fxml/Home.fxml", controller -> {
                        ((GuiHomeView) controller).setAthlete(athleteBean);
                    });
                }
                case PERSONAL_TRAINER -> {
                    PersonalTrainer pt = ptDao.findBy(normalizedEmail); 
                    PersonalTrainerBean ptBean = buildPersonalTrainerBean(user, pt, normalizedEmail);

                    Navigator.navigateTo("/fxml/Homept.fxml", controller -> {
                        ((GuiHomeptView) controller).setPersonalTrainer(ptBean);
                    });
                }
                default -> errorMessageLabel.setText("Ruolo non gestito.");
            }
        } catch (InvalidCredentialsException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (IllegalArgumentException e) {
            errorMessageLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorMessageLabel.setText("Errore inatteso.");
        }
    }

    private AthleteBean buildAthleteBean(User user, Athlete athlete, String normalizedEmail) {
        AthleteBean bean = new AthleteBean();
        bean.setId(user.getId());
        bean.setName(user.getName());
        bean.setSurname(user.getSurname());
        bean.setEmail(normalizedEmail);
        bean.setRole(Role.ATHLETE);

        if (athlete == null) {
            return bean;
        }

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

        return bean;
    }

    private PersonalTrainerBean buildPersonalTrainerBean(User user, PersonalTrainer pt, String normalizedEmail) {
        PersonalTrainerBean bean = new PersonalTrainerBean();
        bean.setId(user.getId());
        bean.setName(user.getName());
        bean.setSurname(user.getSurname());
        bean.setEmail(normalizedEmail);
        bean.setRole(Role.PERSONAL_TRAINER);

        if (pt != null) {
            bean.setActiveUsers(pt.getActiveUsers());
        }

        return bean;
    }

    @FXML
    private void onClickCreateAccount() {
        Navigator.navigateTo("/fxml/Signup.fxml");
    }
}
