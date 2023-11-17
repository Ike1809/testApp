package com.example.tutorappfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable{
    @FXML
    public Button loginButton, registerButton, adminButton, userButton, scheduleMeetingButton, logoutButton;
    public BorderPane rootBorderPane;
    public Label loginStatus;

    public void updateBottomPane(Node content) {
        rootBorderPane.setBottom(content);
    }

    @FXML
    private void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
            Node center = loader.load();
            LoginController centerController = loader.getController();
            centerController.setMainController(this);
            rootBorderPane.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registerScreen.fxml"));
            Node center = loader.load();
            RegisterController centerController = loader.getController();
            centerController.setMainController(this);
            rootBorderPane.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void showAdmin() {
        loadScreen("registerScreen.fxml");
    }

    @FXML
    private void showUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userScreen.fxml"));
            Node center = loader.load();
            UserController centerController = loader.getController();
            centerController.setMainController(this);
            rootBorderPane.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void showScheduleMeeting() {loadScreen("registerScreen.fxml");}

    @FXML
    private void showLogout() {
        showAlert("Logout","Goodbye");
        UserSession.destroyInstance();
        loginStatus.setText("Not logged in...");
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

    private void loadScreen(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
            rootBorderPane.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    // Similarly implement showAdmin, showUser, and showScheduleMeeting
}