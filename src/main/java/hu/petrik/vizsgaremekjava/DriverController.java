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


    private void sqlAlert(SQLException e) {
        alert(Alert.AlertType.ERROR,
                "Hiba történt az adatbázis kapcsolat kialakításakor",
                e.getMessage());
    }
    private void readDrivers() throws SQLException {
        List<Driver> f1 = db.readDriver();
        driverTable.getItems().clear();
        driverTable.getItems().addAll(f1);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
    @FXML
    public void updateClick(ActionEvent event) {
        Driver selected = driverTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                    "Biztos, hogy módosítani szeretné a választott pilotat?", "");
            setStateToUpdate();
            nevInput.setText(selected.getNev());
            korInput.getValueFactory().setValue(selected.getKor());
            nemzetisegInput.setText(selected.getNemzetiseg());
            csapatInput.setText(selected.getCsapat());
            szerzettpontokInput.getValueFactory().setValue(selected.getSzerzettpontok());
            kategoriaInput.setText(selected.getKategoria());
            helyezesInput.getValueFactory().setValue(selected.getHelyezes());
            updateId = selected.getId();
        }
    }

    private void setStateToUpdate() {
        submitButton.setText("Update");
        driverTable.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    private void setStateToSubmit() {
        submitButton.setText("Submit");
        driverTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Driver selected = getSelectedDriver();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                "Biztos, hogy törölni szeretné a választott pilotat?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES)))  {
            return;
        }

        try {
            if (db.deleteDriver(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readDrivers();
        } catch (SQLException e) {
            sqlAlert(e);
        }
        setStateToSubmit();
    }


    private Driver getSelectedDriverNullMsg(String msg) {
        Driver selected = driverTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert(Alert.AlertType.WARNING, "Nincs kijelölt elem", msg);
        }
        return selected;
    }

    private Driver getSelectedDriver() {
        int selectedIndex = driverTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING,
                    "Előbb válasszon ki pilotat a táblázatból", "");
            return null;
        }
        return driverTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String nev = nevInput.getText().trim();
        int kor = korInput.getValue();
        String nemzetiseg = nemzetisegInput.getText().trim();
        String csapat = csapatInput.getText().trim();
        int szerzettpontok = szerzettpontokInput.getValue();
        String kategoria = kategoriaInput.getText().trim();
        int helyezes = helyezesInput.getValue();
        if (nev.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Név megadása kötelező", "");
            return;
        }
        if (nemzetiseg.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Nemzetiség megadása kötelező", "");
            return;
        }
        if (csapat.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Csapat megadása kötelező", "");
            return;
        }
        if (kategoria.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Kategória megadása kötelező", "");
            return;
        }
        if (kor == 0) {
            alert(Alert.AlertType.WARNING, "A kor nem lehet 0", "");
            return;
        }
        if (submitButton.getText().equals("Update")) {
            updateDriver(nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
        } else {
            createDriver(nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
        }
    }

    private void updateDriver(String nev, int kor, String nemzetiseg, String csapat, int szerzettpontok, String kategoria, int helyezes) {
        Driver f1 = new Driver(updateId, nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
        try {
            if (db.updateDriver(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
            }
            readDrivers();
            setStateToSubmit();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }
    private void createDriver(String nev, int kor, String nemzetiseg, String csapat, int szerzettpontok, String kategoria, int helyezes) {
        Driver f1 = new Driver(nev, kor, nemzetiseg, csapat, szerzettpontok, kategoria, helyezes);
        try {
            if (db.createDriver(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
            readDrivers();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        nevInput.setText("");
        korInput.getValueFactory().setValue(0);
        nemzetisegInput.setText("");
        csapatInput.setText("");
        szerzettpontokInput.getValueFactory().setValue(0);
        kategoriaInput.setText("");
        helyezesInput.getValueFactory().setValue(0);

        submitButton.setText("Submit");
        driverTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}