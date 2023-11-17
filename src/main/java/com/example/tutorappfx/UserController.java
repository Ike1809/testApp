package com.example.tutorappfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends MainScreenController implements Initializable {
    @FXML private GridPane scheduleGrid;
    private MainScreenController controller;
    public void setMainController(MainScreenController mainController) {
        this.controller = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateDayLabels();
        populateTimeLabels();
        populateScheduleGrid();
    }

    private void populateTimeLabels() {
        int startHour = 2; // Starting at 2 PM
        for (int i = 0; i <= 7; i++) { // Assuming 8 time slots from 2 PM to 9 PM
            String time = (startHour + i) + " PM";
            Label timeLabel = new Label(time);
            // Add styling if desired
            timeLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
            // Add the label to the first column (index 0) and the appropriate row (i + 1, since i starts at 0)
            scheduleGrid.add(timeLabel, 0, i + 1);
        }
    }

    private void populateDayLabels() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            // Add styling if desired
            dayLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-padding: 5;");
            // Add the label to the appropriate column (i + 1, since i starts at 0) and the first row (index 0)
            scheduleGrid.add(dayLabel, i + 1, 0);
        }
    }
    private void populateScheduleGrid() {
        // Assuming the first row and column are already populated with day and time labels
        int startHour = 2; // 2 PM
        int endHour = 9; // 9 PM
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        for (int row = 1; row <= endHour - startHour + 1; row++) {
            for (int col = 1; col <= daysOfWeek.length; col++) {
                Button slotButton = new Button();
                slotButton.setText("Book"); // Or any other text you want to display

                // Set the preferred size for the buttons or bind them to the grid size
                slotButton.setPrefSize(100, 20);

                // Here you can set an action for each button if needed
                final int selectedHour = startHour + row - 1;
                final String selectedDay = daysOfWeek[col - 1];
                slotButton.setOnAction(event -> handleTimeSlotSelection(selectedHour, selectedDay));

                // Add the button to the GridPane
                scheduleGrid.add(slotButton, col, row);
            }
        }
    }

    private void handleTimeSlotSelection(int hour, String day) {
        // Handle the time slot selection
        System.out.println("Time slot selected: " + day + " at " + hour + ":00");
    }
}
