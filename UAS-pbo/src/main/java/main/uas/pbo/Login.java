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
        dataUser user = operasiuser.login(username, password);

        if (user != null) {
            SessionLogin session = new SessionLogin();
            session.clearSession();
            session.setCurrentUser(user);
            messageLabel.setText("Login successful!");
            user.displayRole();
            try {
                if(user instanceof dataAdmin){
                    
                    App.setRoot("dashboard");
                } else if (user instanceof dataStaff) {
                    App.setRoot("transaksi");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }
}
