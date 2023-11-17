module com.example.tutorappfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tutorappfx to javafx.fxml;
    exports com.example.tutorappfx;
}