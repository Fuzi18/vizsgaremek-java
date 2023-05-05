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

public class KaszniController {
    @FXML
    private TableView<Kaszni> kaszniTable;
    @FXML
    private TableColumn<Kaszni, String> leirasCol;
    @FXML
    private TableColumn<Kaszni, String> kasznikomponensCol;
    @FXML
    private TableColumn<Kaszni, Integer> quantityCol;
    @FXML
    private TableColumn<Kaszni, Integer> priceCol;
    private KaszniDB db;
    @FXML
    private TextField leirasInput;
    @FXML
    private TextField kasznikomponensInput;
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
        kasznikomponensCol.setCellValueFactory(new PropertyValueFactory<>("kasznikomponens"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400));
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 150000000));
        try {
            db = new KaszniDB();
            readKasznis();
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
    private void readKasznis() throws SQLException {
        List<Kaszni> f1 = db.readKaszni();
        kaszniTable.getItems().clear();
        kaszniTable.getItems().addAll(f1);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
    @FXML
    public void updateClick(ActionEvent event) {
        Kaszni selected = kaszniTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                    "Biztos, hogy módosítani szeretné a választott terméket??", "");
            setStateToUpdate();
            leirasInput.setText(selected.getLeiras());
            kasznikomponensInput.setText(selected.getKasznikomponens());
            quantityInput.getValueFactory().setValue(selected.getQuantity());
            priceInput.getValueFactory().setValue(selected.getPrice());
            updateId = selected.getId();
        }
    }

    private void setStateToUpdate() {
        submitButton.setText("Update");
        kaszniTable.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    private void setStateToSubmit() {
        submitButton.setText("Submit");
        kaszniTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Kaszni selected = getSelectedKaszni();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                "Biztos, hogy törölni szeretné a választott terméket?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES)))  {
            return;
        }

        try {
            if (db.deleteKaszni(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readKasznis();
        } catch (SQLException e) {
            sqlAlert(e);
        }
        setStateToSubmit();
    }


    private Kaszni getSelectedKaszniNullMsg(String msg) {
        Kaszni selected = kaszniTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert(Alert.AlertType.WARNING, "Nincs kijelölt elem", msg);
        }
        return selected;
    }

    private Kaszni getSelectedKaszni() {
        int selectedIndex = kaszniTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING,
                    "Előbb válasszon ki egy terméket a táblázatból", "");
            return null;
        }
        return kaszniTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String leiras = leirasInput.getText().trim();
        String kasznikomponens = kasznikomponensInput.getText().trim();
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
        if (kasznikomponens.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Kasznikomponens megadása kötelező", "");
            return;
        }
        if (submitButton.getText().equals("Update")) {
            updateKaszni(leiras, kasznikomponens, quantity, price);
        } else {
            createKaszni(leiras, kasznikomponens, quantity, price);
        }
    }

    private void updateKaszni(String leiras, String kasznikomponens, int quantity, int price) {
        Kaszni f1 = new Kaszni(updateId, leiras, kasznikomponens, quantity, price);
        try {
            if (db.updateKaszni(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
            }
            readKasznis();
            setStateToSubmit();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }
    private void createKaszni(String leiras, String kasznikomponens, int quantity, int price) {
        Kaszni f1 = new Kaszni(leiras, kasznikomponens, quantity, price);
        try {
            if (db.createKaszni(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
            readKasznis();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        leirasInput.setText("");
        kasznikomponensInput.setText("");
        quantityInput.getValueFactory().setValue(0);
        priceInput.getValueFactory().setValue(0);

        submitButton.setText("Submit");
        kaszniTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
