package it.uniroma2.dicii.EzGym;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateWorkoutController {

    @FXML
    private Label dayLabel;
    
    @FXML
    private TextField muscleGroupField;

    @FXML
    private ToggleButton primoGiornoBtn;
    
    @FXML
    private ToggleButton secondoGiornoBtn;
    
    @FXML
    private ToggleButton terzoGiornoBtn;
    
    @FXML
    private ToggleButton quartoGiornoBtn;
    
    @FXML
    private ToggleButton quintoGiornoBtn;
    
    @FXML
    private ToggleButton sestoGiornoBtn;
    
    @FXML
    private Button aggiungiEsercizioBtn;
    
    @FXML
    private Button salvaSchedaBtn;
    
    @FXML
    private AnchorPane scrollPaneContent;
    
    private ToggleGroup dayToggleGroup;
    
    // Mappa per memorizzare gli esercizi di ogni giorno
    private Map<String, VBox> dayExercisesMap;
    
    // Mappa per tenere traccia del contatore di esercizi per ogni giorno
    private Map<String, Integer> dayCounterMap;
    
    // Mappa per memorizzare i gruppi muscolari di ogni giorno
    private Map<String, String> dayMuscleGroupMap;
    
    private String currentDay;

    @FXML
    public void initialize() {
        // Inizializza le mappe
        dayExercisesMap = new HashMap<>();
        dayCounterMap = new HashMap<>();
        dayMuscleGroupMap = new HashMap<>();
        
        // Crea un ToggleGroup per i bottoni dei giorni
        dayToggleGroup = new ToggleGroup();
        
        primoGiornoBtn.setToggleGroup(dayToggleGroup);
        secondoGiornoBtn.setToggleGroup(dayToggleGroup);
        terzoGiornoBtn.setToggleGroup(dayToggleGroup);
        quartoGiornoBtn.setToggleGroup(dayToggleGroup);
        quintoGiornoBtn.setToggleGroup(dayToggleGroup);
        sestoGiornoBtn.setToggleGroup(dayToggleGroup);
        
        // Inizializza le mappe per tutti i giorni
        initializeDayMaps();
        
        // Seleziona il primo giorno di default
        primoGiornoBtn.setSelected(true);
        currentDay = primoGiornoBtn.getText();
        
        // Aggiorna la label con il giorno corrente
        updateDayLabel(currentDay);
        
        // Carica il gruppo muscolare del primo giorno
        loadMuscleGroupForDay(currentDay);
        
        // Mostra gli esercizi del primo giorno
        showDayExercises(currentDay);
        
        // Aggiungi listener al campo gruppo muscolare per salvare i dati quando cambia
        muscleGroupField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (currentDay != null) {
                dayMuscleGroupMap.put(currentDay, newVal);
            }
        });
        
        // Aggiungi listener al bottone "Aggiungi esercizio"
        aggiungiEsercizioBtn.setOnAction(event -> aggiungiEsercizio());
        
        // Aggiungi listener al bottone "Salva scheda"
        salvaSchedaBtn.setOnAction(event -> salvaScheda());
        
        // Listener per il cambio di giorno
        dayToggleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                ToggleButton selectedButton = (ToggleButton) newVal;
                String newDay = selectedButton.getText();
                
                if (!newDay.equals(currentDay)) {
                    // Salva il gruppo muscolare del giorno corrente prima di cambiare
                    if (currentDay != null) {
                        dayMuscleGroupMap.put(currentDay, muscleGroupField.getText());
                    }
                    
                    currentDay = newDay;
                    updateDayLabel(currentDay);
                    loadMuscleGroupForDay(currentDay);
                    showDayExercises(currentDay);
                }
            }
        });
    }

    private void updateDayLabel(String dayName) {
        if (dayLabel != null) {
            dayLabel.setText(dayName);
        }
    }
    
    private void loadMuscleGroupForDay(String dayName) {
        // Carica il gruppo muscolare salvato per questo giorno
        String muscleGroup = dayMuscleGroupMap.getOrDefault(dayName, "");
        muscleGroupField.setText(muscleGroup);
    }

    private void initializeDayMaps() {
        // Inizializza VBox e contatori per ogni giorno
        String[] days = {
            primoGiornoBtn.getText(),
            secondoGiornoBtn.getText(),
            terzoGiornoBtn.getText(),
            quartoGiornoBtn.getText(),
            quintoGiornoBtn.getText(),
            sestoGiornoBtn.getText()
        };
        
        for (String day : days) {
            VBox dayVBox = new VBox(0); // Spacing a 0
            dayVBox.setPrefWidth(720);
            dayExercisesMap.put(day, dayVBox);
            dayCounterMap.put(day, 0);
            dayMuscleGroupMap.put(day, ""); // Inizializza vuoto
        }
    }

    private void showDayExercises(String dayName) {
        // Pulisci il contenuto dello scroll pane
        scrollPaneContent.getChildren().clear();
        
        // Ottieni il VBox con gli esercizi del giorno selezionato
        VBox dayVBox = dayExercisesMap.get(dayName);
        
        if (dayVBox != null) {
            // Aggiungi il VBox del giorno allo scroll pane
            scrollPaneContent.getChildren().add(dayVBox);
            
            // Imposta gli anchor per far occupare tutta la larghezza
            AnchorPane.setLeftAnchor(dayVBox, 0.0);
            AnchorPane.setRightAnchor(dayVBox, 0.0);
            AnchorPane.setTopAnchor(dayVBox, 0.0);
            
            // Aggiorna l'altezza dopo che il layout è stato calcolato
            Platform.runLater(() -> updateScrollPaneHeight(dayVBox));
        }
    }

    private void updateScrollPaneHeight(VBox dayVBox) {
        int numberOfExercises = dayVBox.getChildren().size();
        
        if (numberOfExercises > 0) {
            // Calcola l'altezza totale reale
            double totalHeight = 0;
            
            for (int i = 0; i < numberOfExercises; i++) {
                Parent exercisePane = (Parent) dayVBox.getChildren().get(i);
                
                // Aggiungi il margine superiore per tutti tranne il primo
                if (i > 0) {
                    javafx.geometry.Insets margin = VBox.getMargin(exercisePane);
                    if (margin != null) {
                        totalHeight += margin.getTop();
                    }
                }
                
                // Usa l'altezza preferita del componente
                if (exercisePane instanceof VBox) {
                    VBox vbox = (VBox) exercisePane;
                    totalHeight += vbox.getPrefHeight();
                }
            }
            
            scrollPaneContent.setPrefHeight(Math.max(640.0, totalHeight + 20));
        } else {
            scrollPaneContent.setPrefHeight(640.0);
        }
    }

    private void aggiungiEsercizio() {
        try {
            // Ottieni il toggle button selezionato
            ToggleButton selectedDay = (ToggleButton) dayToggleGroup.getSelectedToggle();
            
            if (selectedDay == null) {
                System.out.println("Nessun giorno selezionato!");
                return;
            }
            
            String dayName = selectedDay.getText();
            
            // Incrementa il contatore per questo giorno
            int currentCounter = dayCounterMap.get(dayName) + 1;
            dayCounterMap.put(dayName, currentCounter);
            
            // Carica il file FXML AggiungiEsercizio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AggiungiEsercizio.fxml"));
            Parent exercisePane = loader.load();
            
            // Ottieni il controller dell'esercizio
            AggiungiEsercizioController controller = loader.getController();
            
            // Imposta il nome del giorno e il numero dell'esercizio
            controller.setDayName(dayName);
            controller.setExerciseNumber(currentCounter);
            
            // Ottieni il VBox del giorno corrente
            VBox dayVBox = dayExercisesMap.get(dayName);
            
            // Imposta il margin superiore solo se non è il primo esercizio
            if (dayVBox.getChildren().size() > 0) {
                javafx.geometry.Insets margin = new javafx.geometry.Insets(15, 0, 0, 0);
                VBox.setMargin(exercisePane, margin);
            }
            
            // Aggiungi l'esercizio
            dayVBox.getChildren().add(exercisePane);
            
            // Aggiungi listener per aggiornare l'altezza quando il TitledPane si espande/chiude
            javafx.scene.control.TitledPane titledPane = controller.getTitledPane();
            if (titledPane != null) {
                // Listener per l'espansione/chiusura
                titledPane.expandedProperty().addListener((obs, oldVal, newVal) -> {
                    Platform.runLater(() -> {
                        // Aggiorna l'altezza del VBox padre in base allo stato
                        if (exercisePane instanceof VBox) {
                            VBox vbox = (VBox) exercisePane;
                            if (newVal) {
                                // Espanso
                                vbox.setPrefHeight(330.0);
                            } else {
                                // Chiuso - usa solo l'altezza dell'header
                                vbox.setPrefHeight(50.0);
                            }
                        }
                        updateScrollPaneHeight(dayVBox);
                    });
                });
                
                // Imposta l'altezza iniziale in base allo stato del TitledPane
                if (exercisePane instanceof VBox) {
                    VBox vbox = (VBox) exercisePane;
                    vbox.setPrefHeight(titledPane.isExpanded() ? 330.0 : 50.0);
                }
            }
            
            // Aggiorna l'altezza dello scroll pane dopo un breve delay per permettere il layout
            Platform.runLater(() -> updateScrollPaneHeight(dayVBox));
            
            System.out.println("Esercizio aggiunto al giorno: " + dayName + " (Esercizio #" + currentCounter + ")");
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore nel caricamento di AggiungiEsercizio.fxml");
        }
    }
    
    private javafx.scene.control.TitledPane findTitledPane(javafx.scene.Node node) {
        if (node instanceof javafx.scene.control.TitledPane) {
            return (javafx.scene.control.TitledPane) node;
        }
        
        if (node instanceof javafx.scene.Parent) {
            for (javafx.scene.Node child : ((javafx.scene.Parent) node).getChildrenUnmodifiable()) {
                javafx.scene.control.TitledPane result = findTitledPane(child);
                if (result != null) {
                    return result;
                }
            }
        }
        
        return null;
    }

    private void salvaScheda() {
        System.out.println("=== Salvataggio scheda ===");
        
        // Salva il gruppo muscolare del giorno corrente
        dayMuscleGroupMap.put(currentDay, muscleGroupField.getText());
        
        // Itera su tutti i giorni
        for (Map.Entry<String, VBox> entry : dayExercisesMap.entrySet()) {
            String dayName = entry.getKey();
            VBox dayVBox = entry.getValue();
            String muscleGroup = dayMuscleGroupMap.get(dayName);
            
            System.out.println("\n" + dayName + " - Gruppo muscolare: " + 
                (muscleGroup.isEmpty() ? "Non specificato" : muscleGroup) + 
                " (" + dayVBox.getChildren().size() + " esercizi):");
            
            // Itera su tutti gli esercizi del giorno
            for (int i = 0; i < dayVBox.getChildren().size(); i++) {
                Parent exercisePane = (Parent) dayVBox.getChildren().get(i);
                System.out.println("  - Esercizio " + (i + 1));
            }
        }
        
        System.out.println("\nScheda salvata con successo!");
    }
    
    // Metodo per ottenere il gruppo muscolare di un giorno specifico
    public String getMuscleGroupForDay(String dayName) {
        return dayMuscleGroupMap.getOrDefault(dayName, "");
    }
    
    // Metodo per ottenere tutti i gruppi muscolari
    public Map<String, String> getAllMuscleGroups() {
        return dayMuscleGroupMap;
    }
    
    // Metodo per ottenere tutti i dati degli esercizi di un giorno specifico
    public void getExerciseDataForDay(String dayName) {
        VBox dayVBox = dayExercisesMap.get(dayName);
        
        if (dayVBox != null) {
            for (int i = 0; i < dayVBox.getChildren().size(); i++) {
                VBox exerciseVBox = (VBox) dayVBox.getChildren().get(i);
                // Implementa la logica per recuperare i dati da AggiungiEsercizioController
            }
        }
    }
    
    // Metodo per ottenere tutti i dati di tutti i giorni
    public Map<String, VBox> getAllExercises() {
        return dayExercisesMap;
    }
}