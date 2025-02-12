package main.uas.pbo;

import javafx.scene.control.Alert;

public class dataAdmin extends dataUser {
    public dataAdmin(int id_user, String username, String password) {
        super(id_user, username, password, "admin");
    }

    @Override
    public void displayRole() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("User Role");
    alert.setHeaderText("Role: " + jabatan);
    alert.setContentText("Selamat Datang " + username + " dan Semangat Bekerja!");
    alert.showAndWait();
    }
}
