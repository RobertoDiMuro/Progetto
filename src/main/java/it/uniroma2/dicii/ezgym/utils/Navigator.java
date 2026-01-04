package it.uniroma2.dicii.ezgym.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Navigator {

    private static Stage mainStage;

    private static final Map<String, Scene> scenes = new HashMap<>();

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void navigateTo(String fxmlPath) {
        navigateTo(fxmlPath, null);
    }

    public static void navigateTo(String fxmlPath, Consumer<Object> controllerSetup) {
        try {
            Scene scene;

            if (scenes.containsKey(fxmlPath)) {
                scene = scenes.get(fxmlPath);

                Object controller = scene.getUserData();
                if (controllerSetup != null) controllerSetup.accept(controller);

                mainStage.setScene(scene);
                mainStage.show();
                return;
            }

            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController();

            scene = new Scene(root);
            scene.setUserData(controller);

            scenes.put(fxmlPath, scene);

            if (controllerSetup != null) controllerSetup.accept(controller);

            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare il file FXML: " + fxmlPath, e);
        }
    }
}
