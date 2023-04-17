package hu.petrik.vizsgaremekjava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DriverController {
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TableColumn<Driver, String> nevCol;
    @FXML
    private TableColumn<Driver, Integer> korCol;
    @FXML
    private TableColumn<Driver, String> nemzetisegCol;
    @FXML
    private TableColumn<Driver, String> csapatCol;
    @FXML
    private TableColumn<Driver, Integer> szerzettpontokCol;
    @FXML
    private TableColumn<Driver, String> kategoriaCol;
    @FXML
    private TableColumn<Driver, Integer> helyezesCol;
    private DriverDB db;
    @FXML
    private TextField nevInput;
    @FXML
    private Spinner<Integer> korInput;
    @FXML
    private TextField nemzetisegInput;
    @FXML
    private TextField csapatInput;
    @FXML
    private Spinner<Integer> szerzettpontokInput;
    @FXML
    private TextField kategoriaInput;
    @FXML
    private Spinner<Integer> helyezesInput;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    private int updateId;

    @FXML
    private void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        korCol.setCellValueFactory(new PropertyValueFactory<>("kor"));
        nemzetisegCol.setCellValueFactory(new PropertyValueFactory<>("nemzetiseg"));
        csapatCol.setCellValueFactory(new PropertyValueFactory<>("csapat"));
        szerzettpontokCol.setCellValueFactory(new PropertyValueFactory<>("szerzettpontok"));
        kategoriaCol.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        helyezesCol.setCellValueFactory(new PropertyValueFactory<>("helyezes"));
        korInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        szerzettpontokInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400));
        helyezesInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        try {
            db = new DriverDB();
            readDrivers();
        } catch (SQLException e) {
            Platform.runLater(() -> {
                sqlAlert(e);
                Platform.exit();
            });
        }

    }

    private void readDrivers() throws SQLException{
        List<Driver> drivers = db.readDriver();
        driverTable.getItems().clear();
        driverTable.getItems().addAll(drivers);
    }

    private void sqlAlert(SQLException e) {
        alert(Alert.AlertType.ERROR,
                "Hiba történt az adatbázis kapcsolat kialakításakor",
                e.getMessage());
    }
}