package main.uas.pbo;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class dataStaff extends dataUser {
    public dataStaff(int id_user, String username, String password) {
        super(id_user, username, password, "staff");
    }

    @Override
    public void displayRole() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("User Role");
    alert.setHeaderText("Role: " + jabatan);
    alert.setContentText("Selamat Datang " + username + " dan Semangat Bekerja!");
    alert.showAndWait();
    }
}
