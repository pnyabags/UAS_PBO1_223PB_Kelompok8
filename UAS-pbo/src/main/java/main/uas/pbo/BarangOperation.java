package main.uas.pbo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class BarangOperation {
    public ObservableList<dataBarang> getBarangList() {
        ObservableList<dataBarang> barangList = FXCollections.observableArrayList();
        String query = "SELECT * FROM barang";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                barangList.add(new dataBarang(
                    resultSet.getInt("id_barang"),
                    resultSet.getString("nama_barang"),
                    resultSet.getString("jenis_barang"),
                    resultSet.getInt("harga"),
                    resultSet.getInt("stok")
                ));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barangList;
    }
    
    public void insertBarang(dataBarang barang) {
        String query = "INSERT INTO barang (nama_barang, jenis_barang, harga, stok) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, barang.getNamaBarang());
            statement.setString(2, barang.getJenisBarang());
            statement.setInt(3, barang.getHarga());
            statement.setInt(4, barang.getStok());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                barang.setIdBarang(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBarang(dataBarang barang) {
        String query = "UPDATE barang SET nama_barang = ?, jenis_barang = ?, harga = ?, stok = ? WHERE id_barang = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, barang.getNamaBarang());
            statement.setString(2, barang.getJenisBarang());
            statement.setInt(3, barang.getHarga());
            statement.setInt(4, barang.getStok());
            statement.setInt(5, barang.getIdBarang());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBarang(int id) {
        String query = "DELETE FROM barang WHERE id_barang = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
