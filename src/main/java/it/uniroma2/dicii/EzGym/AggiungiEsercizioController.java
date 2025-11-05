package it.uniroma2.dicii.ezgym;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AggiungiEsercizioController {

    @FXML
    private TitledPane exerciseNumber;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private Spinner<Integer> setNumberSpinner;
    
    @FXML
    private Spinner<Integer> repsNumberSpinner;
    
    @FXML
    private TextField restField;
    
    @FXML
    private TextField noteField;

    // Variabile per memorizzare il nome del giorno
    private String dayName;

    @FXML
    public void initialize() {
        // Configura gli spinner con valori iniziali
        setupSpinners();
        
        // Aggiungi validazione per il campo recupero (solo numeri)
        restField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                restField.setText(oldValue);
            }
        });
    }

    private void setupSpinners() {
        // Configura lo spinner per il numero di serie (da 1 a 10)
        SpinnerValueFactory<Integer> setValueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
        setNumberSpinner.setValueFactory(setValueFactory);
        setNumberSpinner.setEditable(true);
        
        // Configura lo spinner per il numero di ripetizioni (da 1 a 50)
        SpinnerValueFactory<Integer> repsValueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 10);
        repsNumberSpinner.setValueFactory(repsValueFactory);
        repsNumberSpinner.setEditable(true);
        
        // Aggiungi listener per validare l'input manuale negli spinner
        addSpinnerValidation(setNumberSpinner);
        addSpinnerValidation(repsNumberSpinner);
    }

    private void addSpinnerValidation(Spinner<Integer> spinner) {
        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(oldValue);
            }
        });
        
        // Commit del valore quando si perde il focus
        spinner.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEditorText(spinner);
            }
        });
    }

    private void commitEditorText(Spinner<Integer> spinner) {
        try {
            String text = spinner.getEditor().getText();
            if (!text.isEmpty()) {
                int value = Integer.parseInt(text);
                SpinnerValueFactory<Integer> valueFactory = spinner.getValueFactory();
                
                // Verifica che il valore sia nel range valido
                if (valueFactory instanceof SpinnerValueFactory.IntegerSpinnerValueFactory) {
                    SpinnerValueFactory.IntegerSpinnerValueFactory intFactory = 
                        (SpinnerValueFactory.IntegerSpinnerValueFactory) valueFactory;
                    
                    if (value < intFactory.getMin()) {
                        value = intFactory.getMin();
                    } else if (value > intFactory.getMax()) {
                        value = intFactory.getMax();
                    }
                }
                
                spinner.getValueFactory().setValue(value);
            }
        } catch (NumberFormatException e) {
            // Ripristina il valore corrente se l'input non è valido
            spinner.getEditor().setText(spinner.getValue().toString());
        }
    }

    // Metodo per impostare il nome del giorno
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    // Metodo per impostare il numero dell'esercizio
    public void setExerciseNumber(int number) {
        exerciseNumber.setText("  Esercizio " + number);
    }

    // Metodo getter per il TitledPane
    public TitledPane getTitledPane() {
        return exerciseNumber;
    }

    // Metodi getter per recuperare i dati dell'esercizio
    public String getExerciseName() {
        return nameTextField.getText();
    }

    public int getSetNumber() {
        return setNumberSpinner.getValue();
    }

    public int getRepsNumber() {
        return repsNumberSpinner.getValue();
    }

    public String getRest() {
        return restField.getText();
    }

    public String getNote() {
        return noteField.getText();
    }

    public String getDayName() {
        return dayName;
    }

    // Metodo per validare se tutti i campi obbligatori sono compilati
    public boolean isValid() {
        return !nameTextField.getText().trim().isEmpty() && 
               !restField.getText().trim().isEmpty();
    }

    // Metodo per ottenere tutti i dati come stringa formattata (utile per il debug)
    public String getExerciseData() {
        return String.format(
            "Giorno: %s\nNome: %s\nSerie: %d\nRipetizioni: %d\nRecupero: %s sec\nNote: %s",
            getDayName(),
            getExerciseName(),
            getSetNumber(),
            getRepsNumber(),
            getRest(),
            getNote().isEmpty() ? "Nessuna" : getNote()
        );
    }

    // Metodo per resettare tutti i campi
    public void resetFields() {
        nameTextField.clear();
        setNumberSpinner.getValueFactory().setValue(3);
        repsNumberSpinner.getValueFactory().setValue(10);
        restField.clear();
        noteField.clear();
    }
}