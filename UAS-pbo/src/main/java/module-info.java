module main.uas.pbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens main.uas.pbo to javafx.fxml;
    exports main.uas.pbo;
}
