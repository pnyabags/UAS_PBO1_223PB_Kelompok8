package main.uas.pbo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Dashboard {
    
    @FXML
    private Label resultLabel;
    
    @FXML
    private void dashboard(ActionEvent event) {
        resultLabel.setText("You are in the Dashboard!");
    }
    
    @FXML
    private void barang(ActionEvent event) {
        resultLabel.setText("This is the Transactions page.");
    }
    @FXML
    private void stok(ActionEvent event) {
        resultLabel.setText("This is the Transactions page.");
    }
    
    @FXML
    private void kasir(ActionEvent event) {
        resultLabel.setText("This is the Settings page.");
    }
    
    @FXML
    private void Logout() throws IOException {
        SessionLogin session = new SessionLogin();
        session.clearSession();
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}