package it.uniroma2.dicii.ezgym.view.guiMode;

import java.io.IOException;
import java.util.List;

import it.uniroma2.dicii.ezgym.bean.AthleteBean;
import it.uniroma2.dicii.ezgym.bean.PersonalTrainerBean;
import it.uniroma2.dicii.ezgym.controller.PtRequestcontroller;
import it.uniroma2.dicii.ezgym.dao.abstractFactory.DaoFactory;
import it.uniroma2.dicii.ezgym.dao.interfaceDao.UserDao;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GuiHomeptView {

    @FXML private Label activeCustomerLabel;

    @FXML private Label activeRequestLabel;

    @FXML private Label nameLabel;

    @FXML private VBox requestContainer;
    
    private PersonalTrainerBean currTrainer;
    private PtRequestcontroller ptRequestcontroller;
    private UserDao userDao = DaoFactory.getInstance().createUserDao();

    public void setPersonalTrainer(PersonalTrainerBean personalTrainer){
        this.currTrainer = personalTrainer;
        nameLabel.setText(currTrainer.getName() + "!");
        activeCustomerLabel.setText(String.valueOf(userDao.countAthletes()));
        loadRequests();
    }

    @FXML
    private void initialize(){
        this.ptRequestcontroller = new PtRequestcontroller();
        
    }

    private void loadRequests(){
        try{
            List<AthleteBean> requests = ptRequestcontroller.getAthletesRequest();
            activeRequestLabel.setText(String.valueOf(requests.size()));
            populateRequests(requests);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void populateRequests(List<AthleteBean> requests){

        requestContainer.getChildren().clear();

        for (AthleteBean bean : requests) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SliderBox.fxml"));
                Parent box = loader.load();

                GuiSliderBoxView boxController = loader.getController();
                boxController.setAthleteBean(bean);
                boxController.setPersonalTrainer(currTrainer); 

                requestContainer.getChildren().add(box);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onClickCreateExerciseBtn(){
        Navigator.navigateTo("/fxml/CreaEsercizio.fxml", controller -> {
                    ((GuiCreateExerciseView) controller).setPersonalTrainer(currTrainer);
        });
    }
}
