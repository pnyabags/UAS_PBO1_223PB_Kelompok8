package main.uas.pbo;

import java.sql.*;
import java.util.*;

public class DashboardOperation {
    public int getTotalPendapatan() {
        int dataHarian = 0;
        String query = "SELECT SUM(total) FROM transaksi WHERE DATE(date) = CURDATE()";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();) {
            if (rs.next()) {
                dataHarian = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataHarian;
    }
    public List<String> getPenjualanTertinggi() {
        List<String> topItems = new ArrayList<>();
        String query = "SELECT barang.nama_barang, SUM(transaksi_detail.jumlah) as total_terjual FROM transaksi_detail " +
                     "JOIN barang ON transaksi_detail.id_barang = barang.id_barang " +
                     "GROUP BY barang.nama_barang ORDER BY total_terjual DESC LIMIT 5";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String item = rs.getString("nama_barang") + " - " + rs.getInt("total_terjual") + " Terjual";
                topItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topItems;
    }
    
    public List<String> getStokRendah() {
        List<String> lowStock = new ArrayList<>();
        String query = "SELECT nama_barang, stok FROM barang WHERE stok < 5 ORDER BY stok ASC";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String item = rs.getString("nama_barang") + " - Stok " + rs.getInt("stok");
                lowStock.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lowStock;
    }
}
