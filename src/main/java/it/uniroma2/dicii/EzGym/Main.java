package it.uniroma2.dicii.ezgym;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin123";
    
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        
        primaryStage.setTitle("ezgym - Login");
        
        primaryStage.setResizable(true); 
        primaryStage.setMaximized(false); 
        
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        double windowWidth = 1150.0;
        double windowHeight = 850.0;
        
        if (windowWidth > screenBounds.getWidth()) {
            windowWidth = screenBounds.getWidth() * 0.9; 
        }
        if (windowHeight > screenBounds.getHeight()) {
            windowHeight = screenBounds.getHeight() * 0.9; 
        }
        
        primaryStage.setWidth(windowWidth);
        primaryStage.setHeight(windowHeight);
        primaryStage.setX((screenBounds.getWidth() - windowWidth) / 2);
        primaryStage.setY((screenBounds.getHeight() - windowHeight) / 2);
        
        primaryStage.setFullScreenExitHint("Premi F11 o ESC per uscire dalla modalità schermo intero");
        
        Navigator.setRoot("/fxml/CreaDieta.fxml");
        
        primaryStage.show();
        
        System.out.println("=== ezgym - Applicazione Avviata ===");
        System.out.println("Risoluzione schermo: " + screenBounds.getWidth() + "x" + screenBounds.getHeight());
        System.out.println("Dimensioni finestra: " + windowWidth + "x" + windowHeight);
        System.out.println("Posizione finestra: X=" + primaryStage.getX() + ", Y=" + primaryStage.getY());
        System.out.println("====================================\n");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    
    public static void toggleFullScreen() {
        if (primaryStage != null) {
            primaryStage.setFullScreen(!primaryStage.isFullScreen());
        }
    }

    
    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("\n=== ezgym - Applicazione Chiusa ===");
        
    }
}