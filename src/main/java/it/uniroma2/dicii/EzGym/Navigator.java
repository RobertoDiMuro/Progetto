package it.uniroma2.dicii.EzGym;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;


public class Navigator {

    private static Scene currentScene;
    private static String currentFxml;

    public static void setRoot(String fxml) {
        try {
            Parent root = loadFXML(fxml);
            
            Stage stage = Main.getPrimaryStage();
            
            if (currentScene == null) {
                currentScene = new Scene(root);
                stage.setScene(currentScene);
            } else {
                currentScene.setRoot(root);
            }
            
            updateTitle(fxml, stage);
            
            currentFxml = fxml;
            
            System.out.println("Navigazione verso: " + fxml);
            
        } catch (IOException e) {
            System.err.println("ERRORE: Impossibile caricare il file FXML: " + fxml);
            e.printStackTrace();
            showErrorAlert("Errore di Caricamento", 
                          "Impossibile caricare la schermata: " + fxml + "\n\n" + e.getMessage());
        }
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(Navigator.class.getResource(fxml));
        
        if (loader.getLocation() == null) {
            throw new IOException("File FXML non trovato: " + fxml);
        }
        
        return loader.load();
    }

    private static void updateTitle(String fxml, Stage stage) {
        String fileName = fxml.substring(fxml.lastIndexOf("/") + 1, fxml.lastIndexOf("."));
        
        String title = switch (fileName) {
            case "Login" -> "EzGym - Login";
            case "Registrazione" -> "EzGym - Registrazione";
            case "Home" -> "EzGym - Home";
            case "Scheda" -> "EzGym - Scheda Allenamento";
            case "WorkoutRequest" -> "EzGym - Richiedi Scheda";
            case "Dieta" -> "EzGym - Dieta Personalizzata";
            default -> "EzGym - " + fileName;
        };
        
        stage.setTitle(title);
    }

    
    public static void openInNewWindow(String fxml, String title) {
        try {
            Parent root = loadFXML(fxml);
            
            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(root));
            
            newStage.setMinWidth(400);
            newStage.setMinHeight(300);
            
            newStage.show();
            
            System.out.println("Nuova finestra aperta: " + title);
            
        } catch (IOException e) {
            System.err.println("ERRORE: Impossibile aprire la nuova finestra: " + fxml);
            e.printStackTrace();
            showErrorAlert("Errore", "Impossibile aprire la finestra: " + title);
        }
    }

    
    public static void goBack() {
        
        setRoot("/fxml/Login.fxml");
    }

    
    public static void goToHome() {
        setRoot("/fxml/Home.fxml");
    }

   
    private static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    public static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    public static boolean showConfirmAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        return alert.showAndWait()
                   .filter(response -> response == javafx.scene.control.ButtonType.OK)
                   .isPresent();
    }

    
    public static Scene getCurrentScene() {
        return currentScene;
    }

    
    public static String getCurrentFxml() {
        return currentFxml;
    }
}