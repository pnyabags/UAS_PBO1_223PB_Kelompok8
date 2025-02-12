package main.uas.pbo;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Dashboard {
    @FXML
    private Label pendataanharian;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<String> baranglaris;
    @FXML
    private ListView<String> stokrendah;
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private DashboardOperation dashboard;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    public void initialize() {
        User user = SessionLogin.getCurrentUser();
        if (user != null) {
        welcomeLabel.setText("Selamat Datang, " + user.getUsername());
        } else {
        welcomeLabel.setText("Wow!! Siapa Anda?!");
        }
        dashboard = new DashboardOperation();
        loadDailyRevenue();
        loadTopSellingItems();
        loadLowStockItems();
    }
    
    private void loadDailyRevenue() {
        int pendapatan = dashboard.getTotalPendapatan();
        
        pendataanharian.setText(formatter.format(pendapatan));
    }
    
    private void loadTopSellingItems() {
        List<String> topItems = dashboard.getPenjualanTertinggi();
        baranglaris.getItems().setAll(topItems);
    }
    
    private void loadLowStockItems() {
        List<String> topItems = dashboard.getStokRendah();
        stokrendah.getItems().setAll(topItems);
    }
    
    
    
    @FXML
    private void goDashboard(ActionEvent event) {
        try{
        App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void goBarang(ActionEvent event) {
        try{
        App.setRoot("barang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goLaporan(ActionEvent event) {
        try{
        App.setRoot("laporan");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void goKasir(ActionEvent event) {
        try{
        App.setRoot("transaksi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void Logout() throws IOException {
        SessionLogin session = new SessionLogin();
        session.clearSession();
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Apakah Anda yakin ingin logout?");
        alert.setContentText("Terima Kasih Sudah Bekerja Hari Ini!");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                App.setRoot("login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}