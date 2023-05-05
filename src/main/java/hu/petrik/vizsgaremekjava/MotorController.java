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

public class MotorController {
    @FXML
    private TableView<Motor> motorTable;
    @FXML
    private TableColumn<Motor, String> leirasCol;
    @FXML
    private TableColumn<Motor, String> motorkomponensCol;
    @FXML
    private TableColumn<Motor, Integer> quantityCol;
    @FXML
    private TableColumn<Motor, Integer> priceCol;
    private MotorDB db;
    @FXML
    private TextField leirasInput;
    @FXML
    private TextField motorkomponensInput;
    @FXML
    private Spinner<Integer> quantityInput;
    @FXML
    private Spinner<Integer> priceInput;
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
        leirasCol.setCellValueFactory(new PropertyValueFactory<>("leiras"));
        motorkomponensCol.setCellValueFactory(new PropertyValueFactory<>("motorkomponens"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400));
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 150000000));
        try {
            db = new MotorDB();
            readMotors();
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
    private void readMotors() throws SQLException {
        List<Motor> f1 = db.readMotor();
        motorTable.getItems().clear();
        motorTable.getItems().addAll(f1);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
    @FXML
    public void updateClick(ActionEvent event) {
        Motor selected = motorTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                    "Biztos, hogy módosítani szeretné a választott terméket??", "");
            setStateToUpdate();
            leirasInput.setText(selected.getLeiras());
            motorkomponensInput.setText(selected.getMotorkomponens());
            quantityInput.getValueFactory().setValue(selected.getQuantity());
            priceInput.getValueFactory().setValue(selected.getPrice());
            updateId = selected.getId();
        }
    }

    private void setStateToUpdate() {
        submitButton.setText("Update");
        motorTable.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    private void setStateToSubmit() {
        submitButton.setText("Submit");
        motorTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Motor selected = getSelectedMotor();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                "Biztos, hogy törölni szeretné a választott terméket?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES)))  {
            return;
        }

        try {
            if (db.deleteMotor(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readMotors();
        } catch (SQLException e) {
            sqlAlert(e);
        }
        setStateToSubmit();
    }


    private Motor getSelectedMotorNullMsg(String msg) {
        Motor selected = motorTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert(Alert.AlertType.WARNING, "Nincs kijelölt elem", msg);
        }
        return selected;
    }

    private Motor getSelectedMotor() {
        int selectedIndex = motorTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING,
                    "Előbb válasszon ki egy terméket a táblázatból", "");
            return null;
        }
        return motorTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String leiras = leirasInput.getText().trim();
        String motorkomponens = motorkomponensInput.getText().trim();
        int quantity = quantityInput.getValue();
        int price = priceInput.getValue();
        if (updateId <= 0) {
            alert(Alert.AlertType.ERROR,
                    "Hiba", "Előbb válasszon ki terméket a táblázatból.");
            setStateToSubmit();
            return;
        }
        if (leiras.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Leírás megadása kötelező", "");
            return;
        }
        if (motorkomponens.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Motorkomponens megadása kötelező", "");
            return;
        }
        if (submitButton.getText().equals("Update")) {
            updateMotor(leiras, motorkomponens, quantity, price);
        } else {
            createMotor(leiras, motorkomponens, quantity, price);
        }
    }

    private void updateMotor(String leiras, String motorkomponens, int quantity, int price) {
        Motor f1 = new Motor(updateId, leiras, motorkomponens, quantity, price);
        try {
            if (db.updateMotor(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
            }
            readMotors();
            setStateToSubmit();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }
    private void createMotor(String leiras, String motorkomponens, int quantity, int price) {
        Motor f1 = new Motor(leiras, motorkomponens, quantity, price);
        try {
            if (db.createMotor(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
            readMotors();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        leirasInput.setText("");
        motorkomponensInput.setText("");
        quantityInput.getValueFactory().setValue(0);
        priceInput.getValueFactory().setValue(0);

        submitButton.setText("Submit");
        motorTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
