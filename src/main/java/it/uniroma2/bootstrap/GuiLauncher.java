package it.uniroma2.bootstrap;

import it.uniroma2.dicii.ezgym.dao.abstractfactory.DaoFactory;
import it.uniroma2.dicii.ezgym.utils.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

    private static Mode selectedMode;

    public static void launchGui(Mode mode) {
        selectedMode = mode;
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        DaoFactory.init(selectedMode);

        Navigator.setStage(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("EzGym - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
