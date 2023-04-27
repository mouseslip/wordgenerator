module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.generator.ui to javafx.fxml;
    exports com.generator.ui;
}