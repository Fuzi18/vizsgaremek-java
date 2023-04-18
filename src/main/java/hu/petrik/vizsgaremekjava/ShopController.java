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

}
