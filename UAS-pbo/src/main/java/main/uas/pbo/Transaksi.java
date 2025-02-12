package main.uas.pbo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class Transaksi {
    @FXML
    private TableView<dataBarang> tabelBarang;
    @FXML
    private TableView<dataTransaksi> tabelTransaksi;
    @FXML
    private Label totalHargaLabel;
    @FXML
    private Label kembalianLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField uangPelangganField;
    @FXML
    private ChoiceBox<String> pilihMetodePembayaran;
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
    @FXML
    private TableColumn<dataTransaksi, String> colIdBarangTransaksi;
    @FXML
    private TableColumn<dataTransaksi, Integer> colHargaSatuan;
    @FXML
    private TableColumn<dataTransaksi, Integer> colJumlah;

    private ObservableList<dataTransaksi> daftarTransaksi = FXCollections.observableArrayList();
    private BarangOperation barangOperation = new BarangOperation();
    private TransaksiOperation transaksiOperation = new TransaksiOperation();
    
    Alert alert = new Alert(AlertType.INFORMATION);

    @FXML
    public void initialize() {
        dataUser user = SessionLogin.getCurrentUser();
        if (user != null) { 
            welcomeLabel.setText("Selamat Datang, " + user.getUsername());
        } else { 
            welcomeLabel.setText("Wow!! Siapa Anda?!");
        } 
        pilihMetodePembayaran.setItems(FXCollections.observableArrayList("Tunai", "Debit", "E-Wallet"));
        pilihMetodePembayaran.setValue("Tunai");
        colIdBarang.setCellValueFactory(new PropertyValueFactory<>("IdBarang"));
        colNamaBarang.setCellValueFactory(new PropertyValueFactory<>("NamaBarang"));
        colJenisBarang.setCellValueFactory(new PropertyValueFactory<>("JenisBarang"));
        colHargaBarang.setCellValueFactory(new PropertyValueFactory<>("Harga"));
        colStokBarang.setCellValueFactory(new PropertyValueFactory<>("Stok"));
        colIdBarangTransaksi.setCellValueFactory(new PropertyValueFactory<>("IdBarang"));
        colHargaSatuan.setCellValueFactory(new PropertyValueFactory<>("HargaSatuan"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("Jumlah"));
        setupTableBarang();
        setupTableTransaksi();
        loadBarangData();
        loadTransaksiData();
    }

    private void setupTableBarang() {
        tabelBarang.setOnMouseClicked(this::tambahKeTransaksi);
    }

    private void setupTableTransaksi() {
        
        tabelTransaksi.setOnMouseClicked(this::kurangKeTransaksi);
    }

    private void loadBarangData() {
        tabelBarang.setItems(barangOperation.getBarangList());
    }
    
    private void loadTransaksiData() {
        tabelTransaksi.setItems(daftarTransaksi);
    }

    @FXML
    private void tambahKeTransaksi(MouseEvent event) {
        dataBarang selected = tabelBarang.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getStok() <= 0) {
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("Stok Habis");
            alert.setContentText("Barang yang dipilih sudah habis, tidak dapat ditambahkan ke transaksi.");
            alert.showAndWait();
        } else {
            for (dataTransaksi t : daftarTransaksi) {
                if (t.getIdBarang() == selected.getIdBarang()) {
                    t.tambahJumlah(1);
                    hitungTotal();
                    selected.kurangiStok(1);
                    tabelBarang.refresh();
                    tabelTransaksi.refresh();
                    return;
                }
            }
            // Jika barang belum ada di transaksi, tambahkan baru
            selected.kurangiStok(1);
            String metodePembayaran = pilihMetodePembayaran.getValue();
            daftarTransaksi.add(new dataTransaksi(selected.getIdBarang(),selected.getHarga(), 1, metodePembayaran, selected.getNamaBarang()));
            hitungTotal();
            tabelBarang.refresh();
        }
    }
    
    @FXML
    private void kurangKeTransaksi(MouseEvent event) {
        dataTransaksi selected = tabelTransaksi.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        for (dataTransaksi t : daftarTransaksi) {
            if (t.getIdBarang() == selected.getIdBarang()) {
                // Jika jumlah > 1, kurangi jumlahnya
                if (t.getJumlah() > 1) {
                    t.kurangJumlah(1);
                    // Tambahkan stok kembali ke tabel barang
                    for (dataBarang barang : tabelBarang.getItems()) {
                        if (barang.getIdBarang() == t.getIdBarang()) {
                            barang.tambahStok(1);
                            break;
                        }
                    }
                } else {
                    // Jika jumlah hanya 1, hapus dari daftar transaksi
                    daftarTransaksi.remove(t);
                    // Tambahkan stok kembali ke tabel barang
                    for (dataBarang barang : tabelBarang.getItems()) {
                        if (barang.getIdBarang() == t.getIdBarang()) {
                            barang.tambahStok(1);
                            break;
                        }
                    }
                }
                hitungTotal();
                tabelBarang.refresh();
                tabelTransaksi.refresh();
                return;
            }
        }
    }
    
    public void hitungTotal() {    
        int total = daftarTransaksi.stream().mapToInt(t -> t.getHargaSatuan() * t.getJumlah()).sum();
        totalHargaLabel.setText("Rp. " + total);
    }

    @FXML
    private void submitTransaksi() throws IOException {
        if (daftarTransaksi.isEmpty()) {
            alert.setTitle("Pesan Informasi");
            alert.setHeaderText("ERROR");
            alert.setContentText("Transaksi kosong, tidak bisa diproses!");
            alert.showAndWait();
        } else if (!daftarTransaksi.isEmpty()){
            int uangPelanggan = Integer.parseInt(uangPelangganField.getText());
            String totalHargaText = totalHargaLabel.getText().replace("Rp. ", "");
            int totalHarga = Integer.parseInt(totalHargaText);
            
            if(uangPelanggan >= totalHarga){
            int uangKembalian = uangPelanggan - totalHarga;
            String metodePembayaran = pilihMetodePembayaran.getValue();
            transaksiOperation.submitTransaksi(daftarTransaksi, metodePembayaran);
            transaksiOperation.tampilkanStruk(daftarTransaksi, metodePembayaran, totalHarga, uangKembalian);
            daftarTransaksi.clear();
            kembalianLabel.setText("Rp. " + String.valueOf(uangKembalian));
            totalHargaLabel.setText("Rp. 0");
            loadBarangData();
            }else{
                alert.setTitle("Pesan Informasi");
                alert.setHeaderText("Uang Kurang");
                alert.setContentText("Silahkan memasukan uang dengan benar!");
                alert.showAndWait();
            }
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
