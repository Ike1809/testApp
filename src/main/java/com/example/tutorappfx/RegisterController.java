package com.example.tutorappfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RegisterController extends MainScreenController implements Initializable {
    @FXML private Label loginStatus;
    @FXML private Label lblWelcome;
    @FXML private TextField emailField, firstNameField, surnameField;
    @FXML private PasswordField passwordField, retypePasswordField;
    @FXML private VBox subjectSelectionContainer, registrationDetailsContainer;

    private MainScreenController controller;
    public void setMainController(MainScreenController mainController) {
        this.controller = mainController;
    }

    @FXML
    protected void handleRegister() {
        // Validate input
        if (validateInput()) {
            showAlert("Registration Successful", "User has been registered successfully.");
            lblWelcome.setText("Welcome "+firstNameField.getText());
            // Enable the subject selection section and the complete registration button
            registrationDetailsContainer.setDisable(true);
            subjectSelectionContainer.setDisable(false);
            // Assuming you have fields for email, password, etc.
            String email = emailField.getText();
            String password = passwordField.getText();
            String retypePassword = retypePasswordField.getText();

            // Validate the input first (check for empty fields, matching passwords, etc.)

            try {
                if (password.equals(retypePassword)) {
                    String hashedPassword = hashPassword(password);

                    // Here we append the new user's email and hashed password to the file
                    String userRecord = email + "," + hashedPassword + System.lineSeparator();

                     Files.write(Paths.get("users.txt"), userRecord.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

                    // Inform the user of successful registration
                    // For example, if you have a status label on the form:
                    showAlert("Registration successful!", "Your details have now been saved.");

                    // You might want to navigate the user to the login screen or clear the form
                } else {
                    // Inform the user that the passwords do not match
                    showAlert("Error!", "Passwords do not match.");
                }
            } catch (NoSuchAlgorithmException e) {
                // Handle the exception for the hashing algorithm
                showAlert("Error!", "An error occurred during registration.");
            } catch (IOException e) {
                // Handle the exception for file write operation
                showAlert("Error!", "An error occurred saving user data.");
                throw new RuntimeException(e);
            }
        }else{
            showAlert("Error", "All fields are required to progress!");
        }

        // TODO: Add more comprehensive validation if necessary

        // TODO: Implement registration logic (e.g., storing user data in a database)

        // TODO: Show success message or transition to another screen
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

    private boolean validateInput() {
        // Check if any of the fields are empty
        boolean isInputValid = !emailField.getText().trim().isEmpty() &&
                !firstNameField.getText().trim().isEmpty() &&
                !surnameField.getText().trim().isEmpty() &&
                !passwordField.getText().trim().isEmpty() &&
                !retypePasswordField.getText().trim().isEmpty();

        // Check if passwords match
        if (isInputValid && !passwordField.getText().equals(retypePasswordField.getText())) {
            isInputValid = false;
            showAlert("Error", "Password fields must match required!");
            // Handle password mismatch (e.g., show an error message)
        }

        // Add any other validation rules here

        return isInputValid;
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
