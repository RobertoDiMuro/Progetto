package it.uniroma2.dicii.EzGym;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AggiungiPastoController {

    @FXML private TitledPane mealNumber;
    @FXML private TextField nomePastoField;
    @FXML private TextField alimentoField;
    @FXML private TextField quantitaField;
    @FXML private TextField unitaMisuraField;
    @FXML private TextField alimentoField1; // Campo alternative
    @FXML private Button aggiungiAlimentoBtn;
    @FXML private Button eliminaPastoBtn;
    @FXML private TableView<Alimento> alimentiTable;
    @FXML private TableColumn<Alimento, String> alimentoColumn;
    @FXML private TableColumn<Alimento, String> quantitaColumn;
    @FXML private TableColumn<Alimento, String> unitaMisuraColumn;
    @FXML private TableColumn<Alimento, String> azioniColumn;

    private ObservableList<Alimento> alimentiList = FXCollections.observableArrayList();
    private CreaDietaController parentController;
    private VBox thisView;
    private String nomePasto = "";
    private String giorno;
    private int numeroMeal;

    @FXML
    public void initialize() {
        setupTable();
        
        // Aggiungi listener per il campo nome pasto
        nomePastoField.textProperty().addListener((observable, oldValue, newValue) -> {
            nomePasto = newValue;
            updateMealTitle();
            // Aggiorna anche tutti gli alimenti già inseriti con il nuovo nome pasto
            for (Alimento alimento : alimentiList) {
                alimento.nomePasto.set(newValue);
            }
        });
        
        // Listener per collassare/espandere il TitledPane
        mealNumber.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (thisView != null) {
                // Forza il ricalcolo del layout
                thisView.requestLayout();
            }
        });
        
        aggiungiAlimentoBtn.setOnAction(e -> aggiungiAlimento());
        eliminaPastoBtn.setOnAction(e -> eliminaPasto());
    }

    public void setMealInfo(String giorno, int numeroMeal) {
        this.giorno = giorno;
        this.numeroMeal = numeroMeal;
        updateMealTitle();
    }

    private void updateMealTitle() {
        String title = giorno + " - Pasto " + numeroMeal;
        if (!nomePasto.isEmpty()) {
            title += " (" + nomePasto + ")";
        }
        mealNumber.setText(title);
    }

    public void setParentController(CreaDietaController parent) {
        this.parentController = parent;
        // Ottieni il riferimento al VBox che contiene questo controller
        this.thisView = (VBox) mealNumber.getParent();
    }

    private void setupTable() {
        // Configura la colonna Nome pasto (prima colonna)
        TableColumn<Alimento, String> nomePastoColumn = (TableColumn<Alimento, String>) alimentiTable.getColumns().get(0);
        nomePastoColumn.setCellValueFactory(new PropertyValueFactory<>("nomePasto"));
        
        // Configura le altre colonne
        alimentoColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        quantitaColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        unitaMisuraColumn.setCellValueFactory(new PropertyValueFactory<>("unitaMisura"));
        azioniColumn.setCellValueFactory(new PropertyValueFactory<>("alternative"));
        
        // Rinomina la colonna "Alternative"
        azioniColumn.setText("Alternative");

        alimentiTable.setItems(alimentiList);
        
        // Messaggio quando la tabella è vuota
        alimentiTable.setPlaceholder(new Label("Nessun alimento aggiunto"));
    }

    private void aggiungiAlimento() {
        String alimento = alimentoField.getText().trim();
        String quantita = quantitaField.getText().trim();
        String unita = unitaMisuraField.getText().trim();
        String alternative = alimentoField1.getText().trim();

        // Validazione
        if (alimento.isEmpty() || quantita.isEmpty() || unita.isEmpty()) {
            showError("Compila tutti i campi obbligatori (Alimento, Quantità, Unità di misura)");
            return;
        }

        // Aggiungi alla lista
        Alimento nuovoAlimento = new Alimento(nomePasto, alimento, quantita, unita, alternative);
        alimentiList.add(nuovoAlimento);

        // Pulisci i campi (tranne nome pasto)
        alimentoField.clear();
        quantitaField.clear();
        unitaMisuraField.clear();
        alimentoField1.clear();
    }

    private void eliminaPasto() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma eliminazione");
        alert.setHeaderText("Eliminare questo pasto?");
        alert.setContentText("Tutti gli alimenti associati verranno eliminati.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (parentController != null && thisView != null) {
                    parentController.rimuoviPasto(thisView);
                }
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe interna per rappresentare un alimento
    public static class Alimento {
        private final SimpleStringProperty nomePasto;
        private final SimpleStringProperty nome;
        private final SimpleStringProperty quantita;
        private final SimpleStringProperty unitaMisura;
        private final SimpleStringProperty alternative;

        public Alimento(String nomePasto, String nome, String quantita, String unitaMisura, String alternative) {
            this.nomePasto = new SimpleStringProperty(nomePasto);
            this.nome = new SimpleStringProperty(nome);
            this.quantita = new SimpleStringProperty(quantita);
            this.unitaMisura = new SimpleStringProperty(unitaMisura);
            this.alternative = new SimpleStringProperty(alternative);
        }

        public String getNomePasto() { return nomePasto.get(); }
        public String getNome() { return nome.get(); }
        public String getQuantita() { return quantita.get(); }
        public String getUnitaMisura() { return unitaMisura.get(); }
        public String getAlternative() { return alternative.get(); }

        public SimpleStringProperty nomePastoProperty() { return nomePasto; }
        public SimpleStringProperty nomeProperty() { return nome; }
        public SimpleStringProperty quantitaProperty() { return quantita; }
        public SimpleStringProperty unitaMisuraProperty() { return unitaMisura; }
        public SimpleStringProperty alternativeProperty() { return alternative; }
    }

    // Metodo pubblico per ottenere la lista degli alimenti (utile per il salvataggio)
    public ObservableList<Alimento> getAlimenti() {
        return alimentiList;
    }

    public String getNomePasto() {
        return nomePasto;
    }
}