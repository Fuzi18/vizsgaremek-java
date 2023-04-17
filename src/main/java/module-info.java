module hu.petrik.vizsgaremekjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hu.petrik.vizsgaremekjava to javafx.fxml;
    exports hu.petrik.vizsgaremekjava;
}