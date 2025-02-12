package main.uas.pbo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Laporan {
    
    @FXML 
    private Button btnpenjualanCSV;
    @FXML 
    private Button btnpendapatanhariCSV;
    @FXML 
    private Button btnpendapatanbulanCSV;
    @FXML 
    private Button btnpendapatantahunCSV;
    @FXML
    private Label welcomeLabel;
    @FXML 
    private DatePicker DatePendapatan;
    @FXML
    private TableView<dataTransaksiDetail> tabelTransaksiDetail;
    @FXML
    private TableView<dataLaporan> tabelTransaksi;
    @FXML
    private TableColumn<dataLaporan, Integer> colIdTransaksi;
    @FXML
    private TableColumn<dataTransaksiDetail, Integer> colIdTransaksiDetail;
    @FXML
    private TableColumn<dataTransaksiDetail, String> colNamaBarang;
    @FXML
    private TableColumn<dataLaporan, String> colMetodePembayaran;
    @FXML
    private TableColumn<dataLaporan, Integer> colTotal;
    @FXML
    private TableColumn<dataTransaksiDetail, Integer> colJumlah;
    @FXML
    private TableColumn<dataLaporan, Date> colDate;
    
    private LaporanOperation laporanOperation = new LaporanOperation();
    
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    public void initialize() {
        dataUser user = SessionLogin.getCurrentUser();
        if (user != null) { 
            welcomeLabel.setText("Selamat Datang, " + user.getUsername());
        } else { 
            welcomeLabel.setText("Wow!! Siapa Anda?!");
        }     
        setupTableTransaksiDetail();
        setupTableLaporan();
    }

    private void setupTableTransaksiDetail() {
        colIdTransaksiDetail.setCellValueFactory(new PropertyValueFactory<>("IdTransaksi"));
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("NamaBarang"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("Jumlah"));
        tabelTransaksiDetail.setItems(laporanOperation.getTransaksiDetailList());
    }

    private void setupTableLaporan() {
        colIdTransaksi.setCellValueFactory(new PropertyValueFactory<>("IdTransaksi"));
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("NamaBarang"));
        colMetodePembayaran.setCellValueFactory(new PropertyValueFactory<>("MetodePembayaran"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tabelTransaksi.setItems(laporanOperation.getLaporanList());
    }
    
    @FXML
    private void btnpenjualanCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setTitle("Simpan Laporan Penjualan Barang");
        fileChooser.setInitialFileName("Laporan_Penjualan_Terbanyak.csv");

        Stage stage = (Stage) btnpenjualanCSV.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (Connection connection = databaseConnection.getConnection();
                 FileWriter writer = new FileWriter(file)) {

                // Header CSV
                writer.append("Nama Barang, Jumlah Terjual\n");

                // Query untuk mendapatkan barang dengan transaksi terbanyak
                String query = "SELECT b.nama_barang, SUM(td.jumlah) AS jumlah_terjual FROM transaksi_detail td JOIN barang b ON td.id_barang = b.id_barang GROUP BY b.nama_barang ORDER BY jumlah_terjual DESC";

                try (PreparedStatement statement = connection.prepareStatement(query);
                     ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        String barang = resultSet.getString("nama_barang");
                        int jumlah = resultSet.getInt("jumlah_terjual");
                        
                        writer.append(barang).append(", ").append(String.valueOf(jumlah)).append("\n");
                    }
                }

                writer.flush();
                System.out.println("Laporan Penjualan Barang berhasil diekspor ke CSV!");
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void btnpendapatanhariCSV() {
        if (DatePendapatan.getValue() == null) {
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("Pilih Tanggal");
            alert.setContentText("Anda belum memilih tanggal!");
            alert.showAndWait();
        } else {
            LocalDate selectedDate = DatePendapatan.getValue(); 
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setTitle("Simpan Laporan Pendapatan");
            fileChooser.setInitialFileName("Laporan_Pendapatan_Hari_"+ Date.valueOf(selectedDate) +".csv");

            Stage stage = (Stage) btnpendapatanhariCSV.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (Connection connection = databaseConnection.getConnection();
                     FileWriter writer = new FileWriter(file)) {

                    // Header CSV
                    writer.append("Periode, Total Pendapatan\n");

                    // Query untuk mendapatkan total pendapatan per hari
                    String query = "SELECT DATE(date) AS periode, total AS total_pendapatan "
                                    + "FROM transaksi WHERE DATE(date) = ? "
                                    + "ORDER BY DATE(date) DESC";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, Date.valueOf(selectedDate)); // Set parameter tanggal
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String tanggal = resultSet.getString("periode"); // Ambil periode
                        double totalPendapatan = resultSet.getDouble("total_pendapatan");

                        writer.append(tanggal).append(",").append(String.valueOf(totalPendapatan)).append("\n");
                    }
                }
            }

                    writer.flush();
                    alert.setTitle("Pesan Informasi");
                    alert.setHeaderText("Berhasil Diexport");
                    alert.setContentText("Terima Kasih!");
                    alert.showAndWait();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void btnpendapatanbulanCSV() {
        if (DatePendapatan.getValue() == null) {
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("Pilih Tanggal");
            alert.setContentText("Anda belum memilih tanggal!");
            alert.showAndWait();
        } else {
            LocalDate selectedDate = DatePendapatan.getValue(); 
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setTitle("Simpan Laporan Pendapatan");
            fileChooser.setInitialFileName("Laporan_Pendapatan_Bulan_"+ Date.valueOf(selectedDate) +".csv");

            Stage stage = (Stage) btnpendapatanbulanCSV.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (Connection connection = databaseConnection.getConnection();
                     FileWriter writer = new FileWriter(file)) {

                    // Header CSV
                    writer.append("Periode, Total Pendapatan\n");

                    // Query untuk mendapatkan total pendapatan per hari
                    String query = "SELECT DATE(date) AS periode, total AS total_pendapatan "
                                    + "FROM transaksi WHERE MONTH(date) = ? AND YEAR(date) = ?"
                                    + "ORDER BY MONTH(date) DESC";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, Date.valueOf(selectedDate)); // Set parameter tanggal
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String tanggal = resultSet.getString("periode"); // Ambil periode
                        double totalPendapatan = resultSet.getDouble("total_pendapatan");

                        writer.append(tanggal).append(",").append(String.valueOf(totalPendapatan)).append("\n");
                    }
                }
            }

                    writer.flush();
                    alert.setTitle("Pesan Informasi");
                    alert.setHeaderText("Berhasil Diexport");
                    alert.setContentText("Terima Kasih!");
                    alert.showAndWait();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void btnpendapatantahunCSV() {
        if (DatePendapatan.getValue() == null) {
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("Pilih Tanggal");
            alert.setContentText("Anda belum memilih tanggal!");
            alert.showAndWait();
        } else {
            LocalDate selectedDate = DatePendapatan.getValue(); 
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setTitle("Simpan Laporan Pendapatan");
            fileChooser.setInitialFileName("Laporan_Pendapatan_Tahun_"+ Date.valueOf(selectedDate) +".csv");

            Stage stage = (Stage) btnpendapatantahunCSV.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (Connection connection = databaseConnection.getConnection();
                     FileWriter writer = new FileWriter(file)) {

                    // Header CSV
                    writer.append("Periode, Total Pendapatan\n");

                    // Query untuk mendapatkan total pendapatan per hari
                    String query = "SELECT DATE(date) AS periode, total AS total_pendapatan "
                                    + "FROM transaksi WHERE YEAR(date) = ? "
                                    + "ORDER BY YEAR(date) DESC";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, Date.valueOf(selectedDate)); // Set parameter tanggal
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String tanggal = resultSet.getString("periode"); // Ambil periode
                        double totalPendapatan = resultSet.getDouble("total_pendapatan");

                        writer.append(tanggal).append(",").append(String.valueOf(totalPendapatan)).append("\n");
                    }
                }
            }

                    writer.flush();
                    alert.setTitle("Pesan Informasi");
                    alert.setHeaderText("Berhasil Diexport");
                    alert.setContentText("Terima Kasih!");
                    alert.showAndWait();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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