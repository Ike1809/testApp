package com.example.tutorappfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends MainScreenController implements Initializable {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private MainScreenController controller;
    public void setMainController(MainScreenController mainController) {
        this.controller = mainController;
    }

    @FXML
    protected void handleLogin() throws IOException {
        if (UserSession.getInstance() != null) {
            showAlert("Login status","Another user is already logged in.");
            return;
        }
        // Read the users' credentials from the text file and check if the user exists
        if (checkCredentials(emailField.getText(), passwordField.getText())) {
            // Handle successful login
            showAlert("Login successful!", "Welcome back "+emailField.getText());
            UserSession.getInstance(emailField.getText());
            controller.loginStatus.setText("Logged in as: " + UserSession.getInstance().getUserName());
            controller.userButton.setVisible(true);
            controller.adminButton.setVisible(true);
            controller.scheduleMeetingButton.setVisible(true);
            controller.adminButton.setDisable(true);
            controller.logoutButton.setDisable(false);
            controller.loginButton.setDisable(true);
            controller.registerButton.setDisable(true);

        } else {
            // Handle failed login
            emailField.clear();
            passwordField.clear();
            showAlert("Login failed!", "User does not exist or wrong password.");
            return;
        }
        // TODO: On successful login, make admin, user, and schedule buttons visible in MainScreenController
    }
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedhash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private boolean checkCredentials(String email, String password) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.txt"));
            String hashedPassword = hashPassword(password);

            for (String line : lines) {
                String[] credentials = line.split(","); // Assuming the file has email,hashedPassword format
                if (credentials[0].equals(email) && credentials[1].equals(hashedPassword)) {
                    return true; // User found with matching hashed password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions, possibly update a label to inform the user
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle the exception for the hashing algorithm
        }
        return false; // User not found or error occurred
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

