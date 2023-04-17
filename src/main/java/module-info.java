module hu.petrik.vizsgaremekjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.vizsgaremekjava to javafx.fxml;
    exports hu.petrik.vizsgaremekjava;
}