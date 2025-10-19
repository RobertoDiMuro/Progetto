package it.uniroma2.dicii.EzGym;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreaDietaController {

    @FXML private ToggleButton lunediBtn, martediBtn, mercolediBtn, giovediBtn, venerdiBtn, sabatoBtn, domenicaBtn;
    @FXML private Label dayLabel;
    @FXML private AnchorPane scrollPaneContent;
    @FXML private Button aggiungiPastoBtn;
    @FXML private Button salvaSchedaBtn;

    private ToggleGroup dayToggleGroup;
    private String selectedDay = "Lunedì";
    
    // Mappa per memorizzare i VBox di ogni giorno
    private Map<String, VBox> dayContainers = new HashMap<>();
    
    // Contatore dei pasti per giorno
    private Map<String, Integer> mealCounters = new HashMap<>();

    @FXML
    public void initialize() {
        setupDayButtons();
        initializeDayContainers();
        
        aggiungiPastoBtn.setOnAction(e -> aggiungiPasto());
        salvaSchedaBtn.setOnAction(e -> salvaDieta());
    }

    private void setupDayButtons() {
        dayToggleGroup = new ToggleGroup();
        
        lunediBtn.setToggleGroup(dayToggleGroup);
        martediBtn.setToggleGroup(dayToggleGroup);
        mercolediBtn.setToggleGroup(dayToggleGroup);
        giovediBtn.setToggleGroup(dayToggleGroup);
        venerdiBtn.setToggleGroup(dayToggleGroup);
        sabatoBtn.setToggleGroup(dayToggleGroup);
        domenicaBtn.setToggleGroup(dayToggleGroup);

        // Seleziona lunedì di default
        lunediBtn.setSelected(true);

        // Listener per cambio giorno
        lunediBtn.setOnAction(e -> cambiaGiorno("Lunedì"));
        martediBtn.setOnAction(e -> cambiaGiorno("Martedì"));
        mercolediBtn.setOnAction(e -> cambiaGiorno("Mercoledì"));
        giovediBtn.setOnAction(e -> cambiaGiorno("Giovedì"));
        venerdiBtn.setOnAction(e -> cambiaGiorno("Venerdì"));
        sabatoBtn.setOnAction(e -> cambiaGiorno("Sabato"));
        domenicaBtn.setOnAction(e -> cambiaGiorno("Domenica"));
    }

    private void initializeDayContainers() {
        String[] days = {"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"};
        
        for (String day : days) {
            VBox container = new VBox(15); // Spacing tra i pasti
            container.setPadding(new Insets(10));
            dayContainers.put(day, container);
            mealCounters.put(day, 0);
        }
        
        // Mostra il container di lunedì
        scrollPaneContent.getChildren().clear();
        scrollPaneContent.getChildren().add(dayContainers.get("Lunedì"));
        
        // Imposta il VBox per riempire l'AnchorPane
        AnchorPane.setTopAnchor(dayContainers.get("Lunedì"), 0.0);
        AnchorPane.setLeftAnchor(dayContainers.get("Lunedì"), 0.0);
        AnchorPane.setRightAnchor(dayContainers.get("Lunedì"), 0.0);
    }

    private void cambiaGiorno(String giorno) {
        selectedDay = giorno;
        dayLabel.setText(giorno);
        
        // Cambia il contenuto dello ScrollPane
        scrollPaneContent.getChildren().clear();
        VBox container = dayContainers.get(giorno);
        scrollPaneContent.getChildren().add(container);
        
        // Ancora il VBox all'AnchorPane
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
    }

    private void aggiungiPasto() {
        try {
            // Incrementa il contatore per il giorno corrente
            int mealNumber = mealCounters.get(selectedDay) + 1;
            mealCounters.put(selectedDay, mealNumber);
            
            // Carica il componente AggiungiPasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AggiungiPasto.fxml"));
            VBox pastoView = loader.load();
            
            // Ottieni il controller e passa le informazioni necessarie
            AggiungiPastoController controller = loader.getController();
            controller.setMealInfo(selectedDay, mealNumber);
            controller.setParentController(this);
            
            // Aggiungi il nuovo pasto al container del giorno corrente
            VBox currentContainer = dayContainers.get(selectedDay);
            currentContainer.getChildren().add(pastoView);
            
            // Aggiungi spaziatura tra i pasti
            VBox.setMargin(pastoView, new Insets(0, 0, 15, 0));
            
        } catch (IOException e) {
            e.printStackTrace();
            showError("Errore nel caricamento del componente pasto");
        }
    }

    // Metodo chiamato dal controller figlio per rimuovere un pasto
    public void rimuoviPasto(VBox pastoView) {
        VBox currentContainer = dayContainers.get(selectedDay);
        currentContainer.getChildren().remove(pastoView);
    }

    private void salvaDieta() {
        // Implementa la logica per salvare la dieta
        System.out.println("Salvataggio dieta...");
        
        // Qui puoi raccogliere tutti i dati dai vari pasti di ogni giorno
        for (Map.Entry<String, VBox> entry : dayContainers.entrySet()) {
            String day = entry.getKey();
            VBox container = entry.getValue();
            System.out.println("Giorno: " + day + " - Numero pasti: " + container.getChildren().size());
        }
        
        showInfo("Dieta salvata con successo!");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}