package main.uas.pbo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TransaksiOperation {
    public void submitTransaksi(ObservableList<dataTransaksi> daftarTransaksi, String metode_pembayaran) {
        String insertQueryTransaksi = "INSERT INTO transaksi (date, total, metode_pembayaran) VALUES (?, ?, ?)";
        
        String insertQueryTransaksiDetail = "INSERT INTO transaksi_detail (id_transaksi, id_barang, jumlah) VALUES (?, ?, ?)";
        
        String updateStokQuery = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";

        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);  // Matikan auto-commit untuk transaksi yang aman
                
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQueryTransaksi, Statement.RETURN_GENERATED_KEYS)) {
                
                insertStatement.setDate(1, Date.valueOf(LocalDate.now()));  // Gunakan tanggal saat ini
                insertStatement.setDouble(2, calculateTotal(daftarTransaksi));  // Hitung total transaksi
                insertStatement.setString(3, metode_pembayaran); // Metode Pembayaran
                // Eksekusi insert transaksi utama
                insertStatement.executeUpdate();

                // Ambil ID transaksi yang baru dibuat
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                int idTransaksi = 0;
                if (generatedKeys.next()) {
                    idTransaksi = generatedKeys.getInt(1);  // ID transaksi yang baru
                }

                // 2. Simpan detail transaksi
                try (PreparedStatement detailStatement = connection.prepareStatement(insertQueryTransaksiDetail)) {
                    for (dataTransaksi datatransaksi : daftarTransaksi) {
                        detailStatement.setInt(1, idTransaksi);  // Menggunakan id_transaksi yang baru
                        detailStatement.setInt(2, datatransaksi.getIdBarang());
                        detailStatement.setInt(3, datatransaksi.getJumlah());
                        detailStatement.executeUpdate();
                    }
                }

                // 3. Update stok barang
                try (PreparedStatement updateStatement = connection.prepareStatement(updateStokQuery)) {
                    for (dataTransaksi datatransaksi : daftarTransaksi) {
                        updateStatement.setInt(1, datatransaksi.getJumlah());
                        updateStatement.setInt(2, datatransaksi.getIdBarang());
                        updateStatement.executeUpdate();
                    }
                }

                // Commit transaksi jika semua langkah berhasil
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();  // Rollback jika ada error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private double calculateTotal(ObservableList<dataTransaksi> daftarTransaksi) {
        double total = 0;
        for (dataTransaksi transaksi : daftarTransaksi) {
            total += transaksi.getHargaSatuan() * transaksi.getJumlah();
        }
        return total;
    }
    
    public void tampilkanStruk(ObservableList<dataTransaksi> daftarTransaksi, String metodePembayaran, double total, double kembalian) {
        StringBuilder struk = new StringBuilder();
        struk.append("========== STRUK PEMBAYARAN ==========\n");
        struk.append("Tanggal      : ").append(LocalDate.now()).append("\n");
        struk.append("======================================\n");
        struk.append(String.format("%-20s %-10s %-10s\n", "Nama Barang", "Jumlah", "Subtotal"));
        struk.append("--------------------------------------\n");

        for (dataTransaksi transaksi : daftarTransaksi) {
            double subtotal = transaksi.getHargaSatuan() * transaksi.getJumlah();
            struk.append(String.format("%-20s %-10d Rp%-10.2f\n", transaksi.getNamaBarang(), transaksi.getJumlah(), subtotal));
        }

        struk.append("--------------------------------------\n");
        struk.append("Metode Pembayaran : ").append(metodePembayaran).append("\n");
        struk.append("Total             : Rp").append(String.format("%.2f", total)).append("\n");
        struk.append("Kembalian         : Rp").append(String.format("%.2f", kembalian)).append("\n");
        struk.append("======================================\n");
        struk.append("Terima kasih telah berbelanja!\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Struk Transaksi");
        alert.setHeaderText("Detail Pembayaran");
        alert.setContentText(struk.toString());
        alert.showAndWait();
    }
}
