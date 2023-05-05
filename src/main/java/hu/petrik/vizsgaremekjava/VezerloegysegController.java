package hu.petrik.vizsgaremekjava;

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

public class VezerloegysegController {
    @FXML
    private TableView<Vezerloegyseg> vezerloegysegTable;
    @FXML
    private TableColumn<Vezerloegyseg, String> leirasCol;
    @FXML
    private TableColumn<Vezerloegyseg, String> vezerloegysegkomponensCol;
    @FXML
    private TableColumn<Vezerloegyseg, Integer> quantityCol;
    @FXML
    private TableColumn<Vezerloegyseg, Integer> priceCol;
    private VezerloegysegDB db;
    @FXML
    private TextField leirasInput;
    @FXML
    private TextField vezerloegysegkomponensInput;
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
        vezerloegysegkomponensCol.setCellValueFactory(new PropertyValueFactory<>("vezerloegysegkomponens"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400));
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 150000000));
        try {
            db = new VezerloegysegDB();
            readVezerloegysegs();
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
    private void readVezerloegysegs() throws SQLException {
        List<Vezerloegyseg> f1 = db.readVezerloegyseg();
        vezerloegysegTable.getItems().clear();
        vezerloegysegTable.getItems().addAll(f1);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
    @FXML
    public void updateClick(ActionEvent event) {
        Vezerloegyseg selected = vezerloegysegTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                    "Biztos, hogy módosítani szeretné a választott terméket??", "");
            setStateToUpdate();
            leirasInput.setText(selected.getLeiras());
            vezerloegysegkomponensInput.setText(selected.getVezerloegysegkomponens());
            quantityInput.getValueFactory().setValue(selected.getQuantity());
            priceInput.getValueFactory().setValue(selected.getPrice());
            updateId = selected.getId();
        }
    }

    private void setStateToUpdate() {
        submitButton.setText("Update");
        vezerloegysegTable.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    private void setStateToSubmit() {
        submitButton.setText("Submit");
        vezerloegysegTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Vezerloegyseg selected = getSelectedVezerloegyseg();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                "Biztos, hogy törölni szeretné a választott terméket?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES)))  {
            return;
        }

        try {
            if (db.deleteVezerloegyseg(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readVezerloegysegs();
        } catch (SQLException e) {
            sqlAlert(e);
        }
        setStateToSubmit();
    }


    private Vezerloegyseg getSelectedVezerloegysegNullMsg(String msg) {
        Vezerloegyseg selected = vezerloegysegTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert(Alert.AlertType.WARNING, "Nincs kijelölt elem", msg);
        }
        return selected;
    }

    private Vezerloegyseg getSelectedVezerloegyseg() {
        int selectedIndex = vezerloegysegTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING,
                    "Előbb válasszon ki egy terméket a táblázatból", "");
            return null;
        }
        return vezerloegysegTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String leiras = leirasInput.getText().trim();
        String vezerloegysegkomponens = vezerloegysegkomponensInput.getText().trim();
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
        if (vezerloegysegkomponens.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Vezérlőegységkomponens megadása kötelező", "");
            return;
        }
        if (submitButton.getText().equals("Update")) {
            updateVezerloegyseg(leiras, vezerloegysegkomponens, quantity, price);
        } else {
            createVezerloegyseg(leiras, vezerloegysegkomponens, quantity, price);
        }
    }

    private void updateVezerloegyseg(String leiras, String vezerloegysegkomponens, int quantity, int price) {
        Vezerloegyseg f1 = new Vezerloegyseg(updateId, leiras, vezerloegysegkomponens, quantity, price);
        try {
            if (db.updateVezerloegyseg(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
            }
            readVezerloegysegs();
            setStateToSubmit();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }
    private void createVezerloegyseg(String leiras, String vezerloegysegkomponens, int quantity, int price) {
        Vezerloegyseg f1 = new Vezerloegyseg(leiras, vezerloegysegkomponens, quantity, price);
        try {
            if (db.createVezerloegyseg(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
            readVezerloegysegs();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        leirasInput.setText("");
        vezerloegysegkomponensInput.setText("");
        quantityInput.getValueFactory().setValue(0);
        priceInput.getValueFactory().setValue(0);

        submitButton.setText("Submit");
        vezerloegysegTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
