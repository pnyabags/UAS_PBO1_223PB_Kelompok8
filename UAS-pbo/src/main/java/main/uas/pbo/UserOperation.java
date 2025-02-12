package main.uas.pbo;

import java.sql.*;

public class UserOperation {
    public dataUser login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String jabatan = resultSet.getString("jabatan").toLowerCase();
                int id = resultSet.getInt("id_user");
                String user = resultSet.getString("username");
                String pass = resultSet.getString("password");

                if (jabatan.equals("admin")) {
                    return new dataAdmin(id, user, pass);
                } else if (jabatan.equals("staff")) {
                    return new dataStaff(id, user, pass);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
