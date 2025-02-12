package main.uas.pbo;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Barang {
    @FXML
    private Label welcomeLabel;
    @FXML 
    private TextField fieldNamaBarang;
    @FXML 
    private TextField fieldJenisBarang;
    @FXML 
    private TextField fieldHargaBarang;
    @FXML 
    private TextField fieldStokBarang;
    @FXML 
    private TextField editNamaBarang;
    @FXML 
    private TextField editJenisBarang;
    @FXML 
    private TextField editHargaBarang;
    @FXML 
    private TextField editStokBarang;
    @FXML 
    private TextField deleteIdBarang;
    @FXML
    private TableView<dataBarang> tabelBarang;
    @FXML
    private TableColumn<dataBarang, Integer> colIdBarang;
    @FXML
    private TableColumn<dataBarang, String> colNamaBarang;
    @FXML
    private TableColumn<dataBarang, String> colJenisBarang;
    @FXML
    private TableColumn<dataBarang, Integer> colHargaBarang;
    @FXML
    private TableColumn<dataBarang, Integer> colStokBarang;
    
    private BarangOperation barangOperation = new BarangOperation();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    public void initialize() {
        dataUser user = SessionLogin.getCurrentUser();
        if (user != null) {
        welcomeLabel.setText("Selamat Datang, " + user.getUsername());
        } else {
        welcomeLabel.setText("Wow!! Siapa Anda?!");
        }
        tabelBarang.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            // Set nilai barang yang dipilih ke dalam TextField
            editNamaBarang.setText(newValue.getNamaBarang());
            editJenisBarang.setText(newValue.getJenisBarang());
            editHargaBarang.setText(String.valueOf(newValue.getHarga()));
            editStokBarang.setText(String.valueOf(newValue.getStok()));
            deleteIdBarang.setText(String.valueOf(newValue.getIdBarang()));
        }
    });
        setupTable();
        loadBarangData();
    }
    
    private void loadBarangData() {
        tabelBarang.setItems(barangOperation.getBarangList());
    }
    
    private void setupTable() {
        colIdBarang.setCellValueFactory(new PropertyValueFactory<>("IdBarang"));
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("NamaBarang"));
        colJenisBarang.setCellValueFactory(new PropertyValueFactory<>("JenisBarang"));
        colHargaBarang.setCellValueFactory(new PropertyValueFactory<>("Harga"));
        colStokBarang.setCellValueFactory(new PropertyValueFactory<>("Stok"));
    }
    
    @FXML
    private void tambahBarang() {
        if(fieldNamaBarang.getText().isEmpty() || fieldJenisBarang.getText().isEmpty() || fieldHargaBarang.getText().isEmpty() || fieldStokBarang.getText().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("ERROR");
            alert.setContentText("Field kosong, tidak bisa diproses!");
            alert.showAndWait();
        } else {
            try {
                String namaBarang = fieldNamaBarang.getText();
                String jenisBarang = fieldJenisBarang.getText();
                int hargaBarang = Integer.parseInt(fieldHargaBarang.getText());
                int stokBarang = Integer.parseInt(fieldStokBarang.getText());
                dataBarang newBarang = new dataBarang(0, namaBarang, jenisBarang, hargaBarang, stokBarang);
                barangOperation.insertBarang(newBarang);
                fieldNamaBarang.clear();
                fieldJenisBarang.clear();
                fieldHargaBarang.clear();
                fieldStokBarang.clear();
                loadBarangData();
            } catch (NumberFormatException e) {
                alert.setTitle("Pesan Informasi");
                alert.setHeaderText("ERROR");
                alert.setContentText("Harga dan Stok harus berupa angka!");
                alert.showAndWait();
            }
        }
    }
    
    
    @FXML
    private void editBarang() {
        if(editNamaBarang.getText().trim().isEmpty() || 
          editJenisBarang.getText().trim().isEmpty() || 
          editHargaBarang.getText().trim().isEmpty() || 
          editStokBarang.getText().trim().isEmpty()){
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("ERROR");
            alert.setContentText("Field kosong, tidak bisa diproses!");
            alert.showAndWait();
        } else {
            try {
                dataBarang barang = tabelBarang.getSelectionModel().getSelectedItem();
                int idBarang = barang.getIdBarang();
                String namaBarang = editNamaBarang.getText();
                String jenisBarang = editJenisBarang.getText();
                int hargaBarang = Integer.parseInt(editHargaBarang.getText());
                int stokBarang = Integer.parseInt(editStokBarang.getText());
                dataBarang newBarang = new dataBarang(idBarang, namaBarang, jenisBarang, hargaBarang, stokBarang);
                barangOperation.updateBarang(newBarang);
                editNamaBarang.clear();
                editJenisBarang.clear();
                editHargaBarang.clear();
                editStokBarang.clear();
                loadBarangData();
            } catch (NumberFormatException e) {
                alert.setTitle("Pesan Informasi");
                alert.setHeaderText("ERROR");
                alert.setContentText("Harga dan Stok harus berupa angka!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void deleteBarang() {
        dataBarang barang = tabelBarang.getSelectionModel().getSelectedItem();
        if (barang != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Penghapusan");
        alert.setHeaderText("Apakah Anda yakin ingin menghapus barang?");
        alert.setContentText("Barang yang dihapus tidak bisa dikembalikan!");
        Optional<ButtonType> result = alert.showAndWait();
        
            if (result.isPresent() && result.get() == ButtonType.OK) {
                barangOperation.deleteBarang(barang.getIdBarang());
                tabelBarang.getItems().remove(barang);
                deleteIdBarang.clear();
                loadBarangData();
            }
        } else {
            // Show an error message if no item is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tidak ada barang yang dipilih");
            alert.setContentText("Silakan pilih barang yang ingin dihapus.");
            alert.showAndWait();
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
