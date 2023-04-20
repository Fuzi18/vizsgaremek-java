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

public class ShopController {
    @FXML
    private TableView<Shop> shopTable;
    @FXML
    private TableColumn<Shop, String> typeCol;
    @FXML
    private TableColumn<Shop, String> teamCol;
    @FXML
    private TableColumn<Shop, String> sizeCol;
    @FXML
    private TableColumn<Shop, String> colorCol;
    @FXML
    private TableColumn<Shop, Integer> priceCol;
    @FXML
    private TableColumn<Shop, Integer> quantityCol;
    private ShopDB db;
    @FXML
    private TextField typeInput;
    @FXML
    private TextField teamInput;
    @FXML
    private TextField sizeInput;
    @FXML
    private TextField colorInput;
    @FXML
    private Spinner<Integer> priceInput;
    @FXML
    private Spinner<Integer> quantityInput;
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
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000));
        quantityInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 400));
        try {
            db = new ShopDB();
            readShops();
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
    private void readShops() throws SQLException {
        List<Shop> f1 = db.readShop();
        shopTable.getItems().clear();
        shopTable.getItems().addAll(f1);
    }

    private Optional<ButtonType> alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
    @FXML
    public void updateClick(ActionEvent event) {
        Shop selected = shopTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                    "Biztos, hogy módosítani szeretné a választott terméket??", "");
            setStateToUpdate();
            typeInput.setText(selected.getType());
            teamInput.setText(selected.getTeam());
            sizeInput.setText(selected.getSize());
            colorInput.setText(selected.getColor());
            priceInput.getValueFactory().setValue(selected.getPrice());
            quantityInput.getValueFactory().setValue(selected.getQuantity());
            updateId = selected.getId();
        }
    }

    private void setStateToUpdate() {
        submitButton.setText("Update");
        shopTable.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    private void setStateToSubmit() {
        submitButton.setText("Submit");
        shopTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Shop selected = getSelectedShop();
        if (selected == null) return;

        Optional<ButtonType> optionalButtonType = alert(Alert.AlertType.CONFIRMATION,
                "Biztos, hogy törölni szeretné a választott terméket?", "");
        if (optionalButtonType.isEmpty() || !(optionalButtonType.get().equals(ButtonType.OK)) && !(optionalButtonType.get().equals(ButtonType.YES)))  {
            return;
        }

        try {
            if (db.deleteShop(selected.getId())) {
                alert(Alert.AlertType.WARNING, "Sikeres törlés", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen törlés", "");
            }
            readShops();
        } catch (SQLException e) {
            sqlAlert(e);
        }
        setStateToSubmit();
    }


    private Shop getSelectedShopNullMsg(String msg) {
        Shop selected = shopTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert(Alert.AlertType.WARNING, "Nincs kijelölt elem", msg);
        }
        return selected;
    }

    private Shop getSelectedShop() {
        int selectedIndex = shopTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert(Alert.AlertType.WARNING,
                    "Előbb válasszon ki egy terméket a táblázatból", "");
            return null;
        }
        return shopTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String type = typeInput.getText().trim();
        String team = teamInput.getText().trim();
        String size = sizeInput.getText().trim();
        String color = colorInput.getText().trim();
        int price = priceInput.getValue();
        int quantity = quantityInput.getValue();
        if (type.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Típus megadása kötelező", "");
            return;
        }
        if (team.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Csapat megadása kötelező", "");
            return;
        }
        if (size.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Méret megadása kötelező", "");
            return;
        }
        if (color.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Szín megadása kötelező", "");
            return;
        }
        if (submitButton.getText().equals("Update")) {
            updateShop(type, team, size, color, price, quantity);
        } else {
            createShop(type, team, size, color, price, quantity);
        }
    }

    private void updateShop(String type, String team, String size, String color, int price, int quantity) {
        Shop f1 = new Shop(updateId, type, team, size, color, price, quantity);
        try {
            if (db.updateShop(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres módosítás", "");
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen módosítás", "");
            }
            readShops();
            setStateToSubmit();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }
    private void createShop(String type, String team, String size, String color, int price, int quantity) {
        Shop f1 = new Shop(type, team, size, color, price, quantity);
        try {
            if (db.createShop(f1)) {
                alert(Alert.AlertType.WARNING, "Sikeres felvétel", "");
                resetForm();
            } else {
                alert(Alert.AlertType.WARNING, "Sikertelen felvétel", "");
            }
            readShops();
        } catch (SQLException e) {
            sqlAlert(e);
        }
    }

    private void resetForm() {
        typeInput.setText("");
        teamInput.setText("");
        sizeInput.setText("");
        colorInput.setText("");
        priceInput.getValueFactory().setValue(0);
        quantityInput.getValueFactory().setValue(0);

        submitButton.setText("Submit");
        shopTable.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
