package main.uas.pbo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Login{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void pressLogin() throws IOException { 
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        UserOperation operasiuser = new UserOperation();
        User user = operasiuser.Login(username, password);

        if (user != null) {
            SessionLogin session = new SessionLogin();
            session.setCurrentUser(user);
            messageLabel.setText("Login successful!");
            try {
                App.setRoot("dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }
}
