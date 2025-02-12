/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.uas.pbo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LaporanOperation {
    public ObservableList<dataLaporan> getLaporanList() {
        ObservableList<dataLaporan> laporanList = FXCollections.observableArrayList();
        String query = "SELECT * FROM transaksi";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Date sqlDate = resultSet.getDate("date");  // Get the SQL Date
                LocalDate localDate = sqlDate.toLocalDate();  // Convert SQL Date to LocalDate
                laporanList.add(new dataLaporan(
                    resultSet.getInt("id_transaksi"),
                    resultSet.getInt("total"),
                    resultSet.getString("metode_pembayaran"),
                    localDate
                ));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laporanList;
    }
    
    public ObservableList<dataTransaksiDetail> getTransaksiDetailList() {
        ObservableList<dataTransaksiDetail> DetailTransaksiList = FXCollections.observableArrayList();
        String query = "SELECT td.id_transaksi, td.id_barang, b.nama_barang, td.jumlah "
                        + "FROM transaksi_detail td " 
                        + "JOIN barang b ON td.id_barang = b.id_barang" ;

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                DetailTransaksiList.add(new dataTransaksiDetail(
                    resultSet.getInt("id_transaksi"),
                    resultSet.getInt("jumlah"),
                    resultSet.getString("nama_barang")
                ));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DetailTransaksiList;
    }
}
